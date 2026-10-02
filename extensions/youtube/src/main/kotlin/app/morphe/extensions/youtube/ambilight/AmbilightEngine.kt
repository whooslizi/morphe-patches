package app.morphe.extensions.youtube.ambilight

import android.content.Context
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.widget.ImageView

object AmbilightEngine {
    
    @JvmStatic
    fun applyMassiveBloom(context: Context, backgroundView: ImageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Read the user's custom settings from Morphe SharedPreferences
            val prefs = context.getSharedPreferences("morphe_preferences", Context.MODE_PRIVATE)
            val isEnabled = prefs.getBoolean("ambilight_enabled", true)
            
            if (!isEnabled) {
                backgroundView.setRenderEffect(null)
                return
            }

            // Get user's custom blur and spread values from the settings sliders
            // Defaulting to 150f for blur and 1.2f for spread
            val blurRadius = prefs.getFloat("ambilight_blur_radius", 150f)
            val spreadAmount = prefs.getFloat("ambilight_spread_amount", 1.2f)

            // Apply the custom hardware-accelerated blur
            val blurEffect = RenderEffect.createBlurEffect(blurRadius, blurRadius, Shader.TileMode.MIRROR)
            backgroundView.setRenderEffect(blurEffect)
            
            // Stretch the background to act as a bloom spread
            backgroundView.scaleX = spreadAmount
            backgroundView.scaleY = spreadAmount
        }
    }
}
