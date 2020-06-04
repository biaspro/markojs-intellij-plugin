package org.jetbrains.markojs;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MarkoJsFileType extends LanguageFileType {
	public static final String FILE_EXTENSION = "marko";
	public static final MarkoJsFileType INSTANCE = new MarkoJsFileType();

	protected MarkoJsFileType() {
		super(MarkoJsLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public String getName() {
		return "Marko File";
	}

	@NotNull
	@Override
	public String getDescription() {
		return "Marko file";
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return FILE_EXTENSION;
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return Icons.MARKOJS_ICON;
	}
}
