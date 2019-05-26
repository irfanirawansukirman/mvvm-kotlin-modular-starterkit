package id.pamoyanan_dev.l_extras.ext

import android.os.Bundle
import android.support.v4.app.Fragment

inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(argsBuilder: Bundle.() -> Unit):
        FRAGMENT = this.apply { arguments = Bundle().apply(argsBuilder) }
