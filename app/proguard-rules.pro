# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
-dontpreverify
# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class org.parceler.Parceler$$Parcels
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }
-keep class org.gradle.api.plugins { *; }
-keep class com.googlecode.mp4parser.authoring.tracks.mjpeg { *; }

-keepnames class * extends org.parceler.NonParcelRepository$ConverterParcelable {
    public static final ** CREATOR;
}
-keep class cn.pedant.SweetAlert.Rotate3dAnimation {
  public <init>(...);
}
-keep class * implements com.coremedia.iso.boxes.Box {* ; }

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.appyhigh.adsdk.data.** { *; }
-keep class com.all.lubuteam.officereader.model.** { *; }

-dontwarn com.coremedia.iso.boxes.*
-dontwarn com.googlecode.mp4parser.authoring.tracks.mjpeg.**
-dontwarn com.googlecode.mp4parser.authoring.tracks.ttml.*
-dontwarn android.support.**
-dontwarn javax.lang.**
-dontwarn java.lang.**
-dontwarn com.artifex.**
-dontwarn javax.annotation.**
-dontwarn javax.tools.**
-dontwarn com.squareup.**
-dontwarn io.github.**
-dontwarn com.github.**
-dontwarn com.theartofdev.edmodo.**
-dontwarn java.nio.**
-dontwarn org.codehaus.**
-dontwarn cn.pedant.**
-dontwarn com.google.gson.**
-dontwarn android.security.**
-dontwarn android.net.**
-dontwarn android.content.**
-dontwarn android.app.**
-dontwarn com.startapp.android.**
-dontwarn com.google.android.gms.**
-dontwarn org.apache.**
-dontwarn org.ietf.**
-dontwarn org.w3c.**
-dontwarn com.firebase.**
-dontwarn com.fasterxml.jackson.**
-dontwarn com.google.android.**
-dontwarn com.flurry.android.**
-dontwarn com.inmobi.**
-dontwarn com.facebook.ads.**
-dontwarn com.flurry.android.ads.FurryAdNativeListener.**
-dontwarn android.webkit.**
-dontwarn sun.misc.**
-dontwarn com.jirbo.adcolony.**
-dontwarn com.adcolony.**
-dontwarn com.vungle.**
-dontwarn me.everything.providers.android.browser.**
-dontwarn com.flurry.**
-dontwarn java.awt.**,java.beans.**
-dontwarn okhttp3.internal.platform.*
-dontwarn sun.misc.Unsafe
-dontwarn javax.annotation.**
-dontwarn com.viewpagerindicator.**
-dontwarn com.handmark.**
-dontwarn io.card.**
-dontwarn a.c.**
-dontwarn org.gradle.api.plugins.**
-dontwarn com.googlecode.mp4parser.authoring.tracks.mjpeg.**
-dontwarn com.googlecode.mp4parser.authoring.tracks.mjpeg.**
-dontwarn com.wxiwei.office.**
-dontwarn com.microsoft.schemas.office.**
-dontwarn com.wxiwei.office.**
-dontwarn org.etsi.uri.**
-dontwarn org.openxmlformats.schemas.**
-dontwarn schemasMicrosoftComOfficeOffice.**
-dontwarn org.w3.x2000.**
-dontwarn schemasMicrosoftComOfficeExcel.**
-dontwarn schemasMicrosoftComVml.**
-dontwarn androidx.appcompat.widget.**
-dontwarn b.**
-dontwarn com.unity3d.**
-dontwarn com.ironsource.**

-keep public class * extends android.app.Application
-keep class javax.lang.** { *; }
-keep class com.microsoft.schemas.office.** { *; }
-keep class org.etsi.uri.** { *; }
-keep class org.openxmlformats.schemas.** { *; }
-keep class schemasMicrosoftComOfficeOffice.** { *; }
-keep class schemasMicrosoftComOfficeExcel.** { *; }
-keep class schemasMicrosoftComVml.** { *; }
-keep class com.wxiwei.office.** { *; }
-keep class org.w3.x2000.** { *; }
-keep class java.lang.** { *; }
-keep class javax.annotation.** { *; }
-keep class javax.tools.** { *; }
-keep class com.squareup.** { *; }
-keep class io.github.** { *; }
-keep class com.github.** { *; }
-keep class cn.pedant.** { *; }
-keep class android.support.** { *; }
-keep class com.theartofdev.edmodo.** { *; }
-keep class java.nio.** { *; }
-keep class org.codehaus.** { *; }
-keep class com.google.gson.** { *; }
-keep class android.security.** { *; }
-keep class android.net.** { *; }
-keep class android.content.** { *; }
-keep class com.google.android.** { *; }
-keep class com.startapp.android.** { *; }
-keep interface android.support.** { *; }
-keep interface android.app.** { *; }
-keep class org.apache.** { *; }
-keep class org.ietf.** { *; }
-keep class org.w3c.** { *; }
-keep class com.firebase.** { *; }
-keep interface com.fasterxml.jackson.** {*; }
-keep class com.fasterxml.jackson.** { *; }
-keep class com.inmobi.** {*;}
-keep class com.facebook.ads.** {*;}
-keep class android.webkit.** {*;}
-keep class sun.misc.** {*;}
-keep class com.jirbo.adcolony.**{*;}
-keep class com.adcolony.**{*;}
-keep class com.vungle.**{*;}
-dontwarn com.google.ar.core.**
-dontwarn com.ironsource.mediationsdk.**
-keep class com.google.ar.core.* {*;}
-keep class com.ironsource.mediationsdk.* {*;}
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keep class com.mediaplayer.videoplayer.rest.model.** {*;}
-keep public class android.util.FloatMath
-keep class org.openudid.** { *; }
-keep class com.artifex.** { *; }

-keep class com.shockwave.pdfium.**{*;}


 #sdk
#-keep class a.** { *; }
#-keep class b.** { *; }
#-keep class com.unity3d.** { *;}
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int d(...);
    public static int w(...);
    public static int v(...);
    public static int i(...);
    public static int e(...);
    public static int wtf(...);
}

-keep class org.** { *; }
-keep class com.bea.xml.stream.**{*;}
-keep class org.apache.xmlbeans.** { *; }
-keep class com.microsoft.** { *; }
-keep class org.openxmlformats.**{*;}
-keep class com.apache.poi.** { *; }
-keep class schemaorg_apache_xmlbeans.** {*;}
-adaptresourcefilenames
-adaptresourcefilecontents

-keep class io.jsonwebtoken.*.* { *; }
-keepnames class io.jsonwebtoken.* { *; }
-keepnames interface io.jsonwebtoken.* { *; }
-keep class com.appyhigh.adsdk.data.model.adresponse.** { *; }
-keep class com.revenuecat.purchases.** { *; }
-keep public class kotlin.reflect.jvm.internal.impl.** { public *; }

-dontwarn android.supportv1.v4.util.SimpleArrayMap
-dontwarn android.supportv1.v7.appcompat.R$attr
-dontwarn android.supportv1.v7.appcompat.R$bool
-dontwarn android.supportv1.v7.appcompat.R$color
-dontwarn android.supportv1.v7.appcompat.R$dimen
-dontwarn android.supportv1.v7.appcompat.R$drawable
-dontwarn android.supportv1.v7.appcompat.R$id
-dontwarn android.supportv1.v7.appcompat.R$layout
-dontwarn android.supportv1.v7.appcompat.R$string
-dontwarn android.supportv1.v7.appcompat.R$style
-dontwarn android.supportv1.v7.appcompat.R$styleable
-dontwarn com.gemalto.jp2.JP2Decoder
-dontwarn okhttp3.logging.HttpLoggingInterceptor$Level
-dontwarn okhttp3.logging.HttpLoggingInterceptor
-dontwarn retrofit2.Call
-dontwarn retrofit2.Callback
-dontwarn retrofit2.Converter$Factory
-dontwarn retrofit2.Retrofit$Builder
-dontwarn retrofit2.Retrofit
-dontwarn retrofit2.converter.gson.GsonConverterFactory
-dontwarn retrofit2.http.DELETE
-dontwarn retrofit2.http.GET
-dontwarn retrofit2.http.Path
-dontwarn retrofit2.http.Query

-dontwarn javax.naming.Binding
-dontwarn javax.naming.NamingEnumeration
-dontwarn javax.naming.NamingException
-dontwarn javax.naming.directory.Attribute
-dontwarn javax.naming.directory.Attributes
-dontwarn javax.naming.directory.DirContext
-dontwarn javax.naming.directory.InitialDirContext
-dontwarn javax.naming.directory.SearchControls
-dontwarn javax.naming.directory.SearchResult
-dontwarn okhttp3.Cache
-dontwarn okhttp3.ConnectionSpec$Builder
-dontwarn okhttp3.ConnectionSpec
-dontwarn okhttp3.Dispatcher
-dontwarn okhttp3.Interceptor
-dontwarn okhttp3.MediaType
-dontwarn okhttp3.OkHttpClient$Builder
-dontwarn okhttp3.OkHttpClient
-dontwarn okhttp3.ResponseBody
-dontwarn okhttp3.TlsVersion

-dontwarn org.bouncycastle.asn1.bsi.BSIObjectIdentifiers
-dontwarn org.bouncycastle.asn1.cmp.CMPCertificate
-dontwarn org.bouncycastle.asn1.cmp.CMPObjectIdentifiers
-dontwarn org.bouncycastle.asn1.cmp.CertConfirmContent
-dontwarn org.bouncycastle.asn1.cmp.CertStatus
-dontwarn org.bouncycastle.asn1.cmp.InfoTypeAndValue
-dontwarn org.bouncycastle.asn1.cmp.PBMParameter
-dontwarn org.bouncycastle.asn1.cmp.PKIBody
-dontwarn org.bouncycastle.asn1.cmp.PKIFailureInfo
-dontwarn org.bouncycastle.asn1.cmp.PKIFreeText
-dontwarn org.bouncycastle.asn1.cmp.PKIHeader
-dontwarn org.bouncycastle.asn1.cmp.PKIHeaderBuilder
-dontwarn org.bouncycastle.asn1.cmp.PKIMessage
-dontwarn org.bouncycastle.asn1.cmp.PKIStatusInfo
-dontwarn org.bouncycastle.asn1.cmp.RevDetails
-dontwarn org.bouncycastle.asn1.cms.Attribute
-dontwarn org.bouncycastle.asn1.cms.AttributeTable
-dontwarn org.bouncycastle.asn1.cms.Attributes
-dontwarn org.bouncycastle.asn1.cms.AuthEnvelopedData
-dontwarn org.bouncycastle.asn1.cms.AuthenticatedData
-dontwarn org.bouncycastle.asn1.cms.AuthenticatedDataParser
-dontwarn org.bouncycastle.asn1.cms.CMSAlgorithmProtection
-dontwarn org.bouncycastle.asn1.cms.CMSAttributes
-dontwarn org.bouncycastle.asn1.cms.CMSObjectIdentifiers
-dontwarn org.bouncycastle.asn1.cms.CompressedData
-dontwarn org.bouncycastle.asn1.cms.CompressedDataParser
-dontwarn org.bouncycastle.asn1.cms.ContentInfo
-dontwarn org.bouncycastle.asn1.cms.ContentInfoParser
-dontwarn org.bouncycastle.asn1.cms.DigestedData
-dontwarn org.bouncycastle.asn1.cms.EncryptedContentInfo
-dontwarn org.bouncycastle.asn1.cms.EncryptedContentInfoParser
-dontwarn org.bouncycastle.asn1.cms.EncryptedData
-dontwarn org.bouncycastle.asn1.cms.EnvelopedData
-dontwarn org.bouncycastle.asn1.cms.EnvelopedDataParser
-dontwarn org.bouncycastle.asn1.cms.Evidence
-dontwarn org.bouncycastle.asn1.cms.GenericHybridParameters
-dontwarn org.bouncycastle.asn1.cms.IssuerAndSerialNumber
-dontwarn org.bouncycastle.asn1.cms.KEKIdentifier
-dontwarn org.bouncycastle.asn1.cms.KEKRecipientInfo
-dontwarn org.bouncycastle.asn1.cms.KeyAgreeRecipientIdentifier
-dontwarn org.bouncycastle.asn1.cms.KeyAgreeRecipientInfo
-dontwarn org.bouncycastle.asn1.cms.KeyTransRecipientInfo
-dontwarn org.bouncycastle.asn1.cms.MetaData
-dontwarn org.bouncycastle.asn1.cms.OriginatorIdentifierOrKey
-dontwarn org.bouncycastle.asn1.cms.OriginatorInfo
-dontwarn org.bouncycastle.asn1.cms.OriginatorPublicKey
-dontwarn org.bouncycastle.asn1.cms.OtherKeyAttribute
-dontwarn org.bouncycastle.asn1.cms.OtherRevocationInfoFormat
-dontwarn org.bouncycastle.asn1.cms.PasswordRecipientInfo
-dontwarn org.bouncycastle.asn1.cms.RecipientEncryptedKey
-dontwarn org.bouncycastle.asn1.cms.RecipientIdentifier
-dontwarn org.bouncycastle.asn1.cms.RecipientInfo
-dontwarn org.bouncycastle.asn1.cms.RecipientKeyIdentifier
-dontwarn org.bouncycastle.asn1.cms.RsaKemParameters
-dontwarn org.bouncycastle.asn1.cms.SignedData
-dontwarn org.bouncycastle.asn1.cms.SignedDataParser
-dontwarn org.bouncycastle.asn1.cms.SignerIdentifier
-dontwarn org.bouncycastle.asn1.cms.SignerInfo
-dontwarn org.bouncycastle.asn1.cms.Time
-dontwarn org.bouncycastle.asn1.cms.TimeStampAndCRL
-dontwarn org.bouncycastle.asn1.cms.TimeStampTokenEvidence
-dontwarn org.bouncycastle.asn1.cms.TimeStampedData
-dontwarn org.bouncycastle.asn1.cms.TimeStampedDataParser
-dontwarn org.bouncycastle.asn1.cms.ecc.ECCCMSSharedInfo
-dontwarn org.bouncycastle.asn1.cms.ecc.MQVuserKeyingMaterial
-dontwarn org.bouncycastle.asn1.crmf.AttributeTypeAndValue
-dontwarn org.bouncycastle.asn1.crmf.CRMFObjectIdentifiers
-dontwarn org.bouncycastle.asn1.crmf.CertReqMsg
-dontwarn org.bouncycastle.asn1.crmf.CertRequest
-dontwarn org.bouncycastle.asn1.crmf.CertTemplate
-dontwarn org.bouncycastle.asn1.crmf.CertTemplateBuilder
-dontwarn org.bouncycastle.asn1.crmf.Controls
-dontwarn org.bouncycastle.asn1.crmf.EncKeyWithID
-dontwarn org.bouncycastle.asn1.crmf.EncryptedKey
-dontwarn org.bouncycastle.asn1.crmf.EncryptedValue
-dontwarn org.bouncycastle.asn1.crmf.OptionalValidity
-dontwarn org.bouncycastle.asn1.crmf.PKIArchiveOptions
-dontwarn org.bouncycastle.asn1.crmf.PKMACValue
-dontwarn org.bouncycastle.asn1.crmf.POPOPrivKey
-dontwarn org.bouncycastle.asn1.crmf.POPOSigningKey
-dontwarn org.bouncycastle.asn1.crmf.POPOSigningKeyInput
-dontwarn org.bouncycastle.asn1.crmf.ProofOfPossession
-dontwarn org.bouncycastle.asn1.crmf.SubsequentMessage
-dontwarn org.bouncycastle.asn1.dvcs.CertEtcToken
-dontwarn org.bouncycastle.asn1.dvcs.DVCSObjectIdentifiers
-dontwarn org.bouncycastle.asn1.dvcs.DVCSRequest
-dontwarn org.bouncycastle.asn1.dvcs.DVCSRequestInformation
-dontwarn org.bouncycastle.asn1.dvcs.DVCSRequestInformationBuilder
-dontwarn org.bouncycastle.asn1.dvcs.DVCSResponse
-dontwarn org.bouncycastle.asn1.dvcs.DVCSTime
-dontwarn org.bouncycastle.asn1.dvcs.Data
-dontwarn org.bouncycastle.asn1.dvcs.ServiceType
-dontwarn org.bouncycastle.asn1.dvcs.TargetEtcChain
-dontwarn org.bouncycastle.asn1.eac.CVCertificate
-dontwarn org.bouncycastle.asn1.eac.CVCertificateRequest
-dontwarn org.bouncycastle.asn1.eac.CertificateBody
-dontwarn org.bouncycastle.asn1.eac.CertificateHolderAuthorization
-dontwarn org.bouncycastle.asn1.eac.CertificateHolderReference
-dontwarn org.bouncycastle.asn1.eac.CertificationAuthorityReference
-dontwarn org.bouncycastle.asn1.eac.EACObjectIdentifiers
-dontwarn org.bouncycastle.asn1.eac.ECDSAPublicKey
-dontwarn org.bouncycastle.asn1.eac.PackedDate
-dontwarn org.bouncycastle.asn1.eac.PublicKeyDataObject
-dontwarn org.bouncycastle.asn1.eac.RSAPublicKey
-dontwarn org.bouncycastle.asn1.esf.SignaturePolicyIdentifier
-dontwarn org.bouncycastle.asn1.ess.ESSCertID
-dontwarn org.bouncycastle.asn1.ess.ESSCertIDv2
-dontwarn org.bouncycastle.asn1.ess.SigningCertificate
-dontwarn org.bouncycastle.asn1.ess.SigningCertificateV2
-dontwarn org.bouncycastle.asn1.est.AttrOrOID
-dontwarn org.bouncycastle.asn1.est.CsrAttrs
-dontwarn org.bouncycastle.asn1.isismtt.ISISMTTObjectIdentifiers
-dontwarn org.bouncycastle.asn1.tsp.Accuracy
-dontwarn org.bouncycastle.asn1.tsp.MessageImprint
-dontwarn org.bouncycastle.asn1.tsp.PartialHashtree
-dontwarn org.bouncycastle.asn1.tsp.TSTInfo
-dontwarn org.bouncycastle.asn1.tsp.TimeStampReq
-dontwarn org.bouncycastle.asn1.tsp.TimeStampResp
