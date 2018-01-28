# Presentation

An architecture for Android as a replacement of MVC.

## Why should I use Presentation?

Because you want to have more readable, testable code.

Avoid "God Objects", mainly your Activities or Fragments.

## How does it work?

Separation of responsibilities by module:
- `Presenter`: Get a Business Object from the DataProvider and give instructions to the ViewProxy
- `DataProvider`: Communicate with the "outside" to set and get the data, following the instructions of the Presenter
- `ViewProxy`: Convert Presenter instructions and set values to Android Views.

## Architecture

<img src="https://raw.githubusercontent.com/StanKocken/Presentation/master/img_references.png" />

Leak safe.
Don't hold a strong reference to the `DataProvider`, `Presenter`, or `ViewProxy`.

## Sample

The goal is to make this application:

<img src="https://raw.githubusercontent.com/StanKocken/Presentation/master/sample_screenshot.png" />

Each public method of the modules are defined into an interface:

    public interface FormDef {

        interface IPresenter extends Base.IPresenter {

            void onClickSaveButton(String value);
        }

        interface IDataProvider extends Base.IDataProvider {

            String getValueSaved();

            void saveValue(String value);
        }

        interface IView extends Base.IView {

            void setValueSaved(String text);
        }

    }

Then you have your:
- `FormPresenter` that extends `BasePresenter` and implements `FormDef.IPresenter`
- `FormDataProvider` that extends `BaseDataProvider` and implements `FormDef.IDataProvider`
- `FormViewProxy` that extends `BaseViewProxy` and implements `FormDef.IView`

The easiest way to use this architecture is to override `BaseActivity`, even if it's NOT mandatory.

    public class FormActivity extends BaseActivity<FormPresenter, FormViewProxy> {

        @Override
        public int getContentView() {
            return R.layout.activity_form;
        }

        @Override
        public Class<FormPresenter> getPresenterClass() {
            return FormPresenter.class; // the class of your presenter to be used within this activity
        }

        @Override
        public FormViewProxy newViewProxy(FormPresenter presenter, Bundle savedInstanceState) {
            return new FormViewProxy(this); // the view proxy, with the Activity in param
        }
    }

Note: it also exists a `BaseFragment` to do exactly the same but within a Fragment.

## Go further

### Re-use Presenter after Activity destruction

Because `BasePresenter` extends `ViewModel` (from Android Architecture), it is possible to re-use the same Presenter after a rotation for example.

When you are using `BaseActivity`, your presenters will be automatically re-used.

Note: in this case, the DataProvider will also be kept, but you will have to re-create the `ViewProxy`.

### Presenter into an Adapter

This library has a dependency on [Efficient Adapter](https://github.com/StanKocken/EfficientAdapter) to use the same view cache mechanism.

This allows to apply the Presentation pattern to object in an `Adapter` as well. Your `ViewHolder` should extend `PresenterViewHolder` and your `Presenter` should extend `BaseItemPresenter`.


## Proguard

Nothing special needed.

## Gradle

```
dependencies {
    compile 'com.skocken:presentation:2.3.0'
}
```

## License

* [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

## Contributing

Please fork this repository and contribute back using
[pull requests](https://github.com/StanKocken/Presentation/pulls).

Any contributions, large or small, major features, bug fixes, unit/integration tests are welcomed and appreciated
but will be thoroughly reviewed and discussed.
