Change Log
===============================================================================

Version 2.2.0-SNAPSHOT *(2018-01-27)*

* `setProvider()` on the `BasePresenter` now require a generic `Base.IDataProvider`, like any other setPresenter/setView.
<br />Change require for a better compatibility in Kotlin

Version 2.1.1 *(2018-01-27)*

* Changed Null safety on getters for BasePresenter.
<br />Now the `getView()` from the BasePresenter declares itself as `@NonNull` which will crash on Kotlin if it's not the case, as it should.
<br />A new method `getViewOrNull()`, with the annotation `@Nullable` has been introduced to let you check it before using it.
<br />This can be a breaking change for some Kotlin project.

Version 2.0.2 *(2018-01-20)*

* Improve compatibility with Kotlin (Null annotations, reserved keywords…)
* Update Support Library to "27.0.2"

Version 2.0.0 *(2017-11-12)*

* Release 2.0.0

----------------------------

Version 2.0.0-alpha3 *(2016-05-01)*

 * Re-write the management of the lifecycle, using `android.arch.lifecycle` from  [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
 * Use `android.arch.lifecycle` version `1.0.0-alpha3`
 * Added support for Fragment (and DialogFragment)
 * Your `Presenter` now supports configuration changes! Thanks to Android `ViewModel`.
 * `BasePresenter` does not take a `Base.IView` and `Base.IDataProvider` within the constructor anymore. You need to provide them after, with the setters.

 Breaking changes:
 * You *must* provide an empty public constructor for your Presenter.
 * *BasePresenter* does not include the methods `onResume`, `onStart`… anymore. Use the annotation `@OnLifecycleEvent` and implements `LifecycleObserver` instead.
 * Other custom methods from *BasePresenter*, like `onCreateOptionsMenu`, `onOptionsItemSelected`…, are just removed. It created confusions and cannot work in any cases (like if using a Presenter with a View and not an Activity).

----------------------------

Version 1.0.2 *(2016-05-01)*

 * Update dependencies
 * Pushed to JCenter

----------------------------

Version 1.0.1 *(2016-02-29)*
----------------------------

 * Fixed "getActivity()" on Presenter
 * Fixed lifecycle call on Presenter

Version 1.0.0 *(2015-11-15)*
----------------------------

 * Added "setPresenter" into Activities to allow dynamic change of Presenter by children
 * Version 0.1 enough tested, ready to be release as stable version

Version 0.1 *(2011-11-10)*
----------------------------
 * Initial release.