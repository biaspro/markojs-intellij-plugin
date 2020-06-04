package org.jetbrains.markojs;

import com.intellij.lang.Language;

public class MarkoJsLanguage extends Language {
    public static final MarkoJsLanguage INSTANCE = new MarkoJsLanguage();

    private MarkoJsLanguage() {
        super("Marko");
    }
}
