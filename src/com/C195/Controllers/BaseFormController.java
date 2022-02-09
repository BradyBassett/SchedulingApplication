package com.C195.Controllers;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class BaseFormController {
    protected static Locale locale = Locale.getDefault();
    protected static ResourceBundle bundle = setBundle();

    private static ResourceBundle setBundle() {
        return ResourceBundle.getBundle("com.C195.language", locale);
    }
}
