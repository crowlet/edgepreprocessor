package pl.wat.wcy.panek.edgepreprocessor.application;

public interface Message<T extends Number> {
    T getValue();
    String getUserId();
}
