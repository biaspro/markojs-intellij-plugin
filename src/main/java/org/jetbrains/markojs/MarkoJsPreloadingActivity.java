package org.jetbrains.markojs;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.extensions.PluginDescriptor;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.textmate.TextMateService;
import org.jetbrains.plugins.textmate.configuration.BundleConfigBean;
import org.jetbrains.plugins.textmate.configuration.TextMateSettings;
import org.wso2.lsp4intellij.IntellijLanguageClient;
import org.wso2.lsp4intellij.client.languageserver.serverdefinition.RawCommandServerDefinition;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class MarkoJsPreloadingActivity extends PreloadingActivity {
    private static final String BUNDLE = "Marko";
    private static final String PLUGIN_ID = "org.jetbrains.markojs";

    @Override
    public void preload(@NotNull ProgressIndicator indicator) {
        final IdeaPluginDescriptor plugin = PluginManagerCore.getPlugin(PluginId.getId(PLUGIN_ID));
        setupSyntaxHighlighting(plugin);

        final File root = Arrays.stream(PluginManagerCore.getPlugins())
                .filter(d -> PluginId.getId("org.jetbrains.markojs").equals(d.getPluginId()))
                .findFirst()
                .map(PluginDescriptor::getPath)
                .orElseThrow(() -> new IllegalStateException("PluginDescriptor for org.intellij.sdk.language not found."));

        String bin = new File(root, "server/dist/bin.js").getPath();
        RawCommandServerDefinition serverDefinition = new RawCommandServerDefinition("marko", new String[]{"/usr/local/bin/node", bin});
        IntellijLanguageClient.addServerDefinition(serverDefinition);
    }

    private void setupSyntaxHighlighting(final IdeaPluginDescriptor plugin) {
        TextMateSettings.TextMateSettingsState state = TextMateSettings.getInstance().getState();
        if (state == null) {
            state = new TextMateSettings.TextMateSettingsState();
        }

        final String bundlePath = new File(plugin.getPath(), "Marko.tmbundle").getAbsolutePath();
        final BundleConfigBean bundle = new BundleConfigBean(BUNDLE, bundlePath, true);

        state.setBundles(Collections.singleton(bundle));
        TextMateSettings.getInstance().loadState(state);
        TextMateService.getInstance().reloadEnabledBundles();
    }
}
