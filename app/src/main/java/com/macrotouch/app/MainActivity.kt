package com.macrotouch.app
import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.provider.Settings
import android.widget.*
class MainActivity: Activity() {
 override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
 val root=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(32,48,32,24) }
 val title=TextView(this).apply { text="MacroTouch 0.1"; textSize=26f }
 val cycles=EditText(this).apply { hint="Ciclos (1–200)"; inputType=2; setText("10") }
 val wait=EditText(this).apply { hint="Pausa en segundos"; inputType=2; setText("2") }
 fun btn(text:String, action:()->Unit) { root.addView(Button(this).apply { this.text=text; setOnClickListener { action() } }) }
 root.addView(title); root.addView(cycles); root.addView(wait)
 btn("Activar accesibilidad") { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
 btn("Iniciar ciclos") { val n=cycles.text.toString().toIntOrNull()?.coerceIn(1,200)?:10; val s=wait.text.toString().toLongOrNull()?.coerceIn(1,60)?:2L; if(MacroService.instance==null) Toast.makeText(this,"Activa MacroTouch en Accesibilidad",Toast.LENGTH_LONG).show() else MacroService.instance?.start(n,s*1000) }
 btn("Detener") { MacroService.instance?.stop() }
 root.addView(TextView(this).apply { text="Abre Recientes y desliza la tarjeta central hacia arriba. Prueba primero con una app sin datos importantes." })
 setContentView(root)
 }
}
