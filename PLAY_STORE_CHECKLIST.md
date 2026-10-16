# DIVA Play Store launch checklist

## Done in this pass
- [x] Release `usesCleartextTraffic=false` (debug still allows localhost)
- [x] R8 minify + shrink + ProGuard rules
- [x] Release signing config via `keystore.properties`
- [x] versionCode **3** / versionName **1.0.2**
- [x] System back handling (Tracking no longer exits app)
- [x] Location permission no longer auto-blocks Home after login
- [x] Address form fields use OutlinedTextField (more reliable save)
- [x] Privacy / Terms public pages on backend (`/privacy.html`, `/terms.html`)
- [x] Default address reassigned after delete (backend)

## Done locally (machine)
- [x] Release keystore `diva-release.jks` + `keystore.properties` (gitignored — back up both!)
- [x] Signed AAB built: `app/build/outputs/bundle/release/app-release.aab` (~12 MB)

## You must do manually before submit
- [ ] Deploy production API on `https://lookdiva.app` with `APP_DEBUG=false`
- [ ] Confirm Play Console privacy URL: `https://lookdiva.app/privacy.html`
- [ ] Confirm terms URL: `https://lookdiva.app/terms.html`
- [ ] Play Console: store listing, screenshots, feature graphic, Data safety form
- [ ] Content rating questionnaire
- [ ] Target audience / ads declaration
- [ ] Internal testing track first, then production
- [ ] Back up `diva-release.jks` + `keystore.properties` offline (losing them blocks updates)

## Optional polish
- [ ] Firebase / FCM push
- [ ] Google Sign-In
- [ ] Rename Kotlin `namespace` from `com.example.mytestapplication` (applicationId already `com.lookdiva.app`)
