package com.macrotouch.app
import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
class MacroService: AccessibilityService() {
 companion object { var instance:MacroService?=null }
 private val handler=Handler(Looper.getMainLooper())
 private var active=false
 private var token=0
 override fun onServiceConnected(){ super.onServiceConnected(); instance=this }
 override fun onAccessibilityEvent(event:AccessibilityEvent?){}
 override fun onInterrupt(){ stop() }
 override fun onDestroy(){ stop(); instance=null; super.onDestroy() }
 fun stop(){ active=false; token++; handler.removeCallbacksAndMessages(null) }
 fun start(cycles:Int,delay:Long){ stop(); active=true; val current=token; cycle(cycles,delay,current) }
 private fun cycle(left:Int,delay:Long,t:Int){
 if(!active||t!=token||left<=0){ active=false; return }
 performGlobalAction(GLOBAL_ACTION_RECENTS)
 handler.postDelayed({
 if(!active||t!=token)return@postDelayed
 val dm=resources.displayMetrics; val x=dm.widthPixels*.5f; val y=dm.heightPixels*.57f
 val path=Path().apply { moveTo(x,y); lineTo(x,dm.heightPixels*.12f) }
 val gesture=GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(path,0,420)).build()
 dispatchGesture(gesture,object:GestureResultCallback(){
 override fun onCompleted(g:GestureDescription?){ handler.postDelayed({ cycle(left-1,delay,t) },delay) }
 override fun onCancelled(g:GestureDescription?){ stop() }
 },handler)
 },850)
 }
}
