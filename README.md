# Presentation

An architecture for Android as a replacement of MVC.

## Why should I use Presentation?

Because you want to have a more readable, testable code.

Avoid "God Object", mainly your Activities or Fragments.

## How does it work?

Separation of responsibilities by module:
- `Presenter`: get Business Object from the DataProvider and give instructions to the ViewProxy
- `DataProvider`: communicate with "outside" to set and get the data, following the instructions of the Presenter
- `ViewProxy`: convert Presenter instructions and set values to Android Views.

## Architecture

<img src="https://raw.githubusercontent.com/StanKocken/Presentation/master/img_references.png" style="width: 300px;"/>

Leak safe.
Don't hold with a strong reference any of the `DataProvider`, `Presenter` or `ViewProxy`.

## Sample

The goal is to make this application:

<img src="https://raw.githubusercontent.com/StanKocken/Presentation/master/sample_screenshot.png" style="width: 300px;"/>

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
- `FormPresenter` that extends `BasePresenter` and implement this `FormDef.IPresenter`
- `FormViewProxy` that extends `BaseDataProvider` and implement this `FormDef.IDataProvider`
- `FormDataProvider` that extends `BaseViewProxy` and implement this `FormDef.IView`


## Go further

### Presenter into an Adapter

This library has a dependency to [Efficient Adapter](https://github.com/StanKocken/EfficientAdapter) to use the same view cache mechanism.

This allow to apply the Presentation pattern to object into an `Adapter` as well. Your `ViewHolder` should extends `PresenterViewHolder` and your `Presenter` extends `BaseItemPresenter`.

## Proguard

Nothing special needed.

## Gradle

    dependencies {
        compile 'com.skocken:presentation:1.0.0'
    }


## License

* [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

## Contributing

Please fork this repository and contribute back using
[pull requests](https://github.com/StanKocken/Presentation/pulls).

Any contributions, large or small, major features, bug fixes, unit/integration tests are welcomed and appreciated
but will be thoroughly reviewed and discussed.
