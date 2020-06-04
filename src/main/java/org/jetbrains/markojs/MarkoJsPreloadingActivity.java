package org.jetbrains.markojs;

import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.extensions.PluginDescriptor;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.annotations.NotNull;
import org.wso2.lsp4intellij.IntellijLanguageClient;
import org.wso2.lsp4intellij.client.languageserver.serverdefinition.RawCommandServerDefinition;

import java.io.File;
import java.util.Arrays;

public class MarkoJsPreloadingActivity extends PreloadingActivity {
    @Override
    public void preload(@NotNull ProgressIndicator indicator) {
        final File root = Arrays.stream(PluginManagerCore.getPlugins())
                .filter(d -> PluginId.getId("org.jetbrains.markojs").equals(d.getPluginId()))
                .findFirst()
                .map(PluginDescriptor::getPath)
                .orElseThrow(() -> new IllegalStateException("PluginDescriptor for org.intellij.sdk.language not found."));

        String bin = new File(root, "server/dist/bin.js").getPath();
        RawCommandServerDefinition serverDefinition = new RawCommandServerDefinition("marko", new String[]{"/usr/local/bin/node", bin});
        IntellijLanguageClient.addServerDefinition(serverDefinition);
    }
}
