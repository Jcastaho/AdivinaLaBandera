# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Reglas para Firebase
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# Reglas específicas para Google Play Games
-keep class com.google.android.gms.** { *; }
-keep class com.google.android.gms.games.** { *; }
-keep class com.google.games.bridge.** { *; }

# Para Firestore
-keep class com.google.firebase.** { *; }
-keep class com.google.cloud.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.cloud.**

# Para casos específicos de n4.b mencionado en tu error
-keep class T2.** { *; }
-keep class n4.** { *; }

# Deshabilitar optimización específicamente para clases problemáticas
-keep,allowobfuscation,allowoptimization class com.google.firebase.** { *; }
-keep,allowobfuscation,allowoptimization class com.google.android.gms.** { *; }

# O si el problema persiste, puedes intentar esto como último recurso:
-dontoptimize
-dontobfuscate
# Mantener la clase DatosUser intacta