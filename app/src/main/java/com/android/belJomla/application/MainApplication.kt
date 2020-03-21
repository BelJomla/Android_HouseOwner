import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import java.util.*


// Extend the LocalizationApplication
class MainApplication: LocalizationApplication() {
   override fun getDefaultLanguage(): Locale = Locale.ENGLISH
}