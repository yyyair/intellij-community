// Copyright 2000-2025 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.workshop.apk;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileSystem;
import jadx.api.JadxArgs;
import jadx.core.dex.nodes.RootNode;
import jadx.core.xmlgen.BinaryXMLParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class APKFileSystem extends VirtualFileSystem {
  public String MANIFEST_PATH = "/AndroidManifest.xml";
  public static final String PROTOCOL = "apk";
  protected VirtualFile myApk;
  private VirtualFile manifest;
  private RootNode root;
  public APKFileSystem() {
    Logger.getInstance(APKFileSystem.class).debug("Inited");
  }

  public void setApk(VirtualFile apk) {
    myApk = apk;
    root = new RootNode(new JadxArgs());
  }

  @Override
  public @NotNull String getProtocol() {
    return PROTOCOL;
  }

  @Override
  public @Nullable VirtualFile findFileByPath(@NotNull String path) {
    if (path.equals(MANIFEST_PATH)) {
      return manifest;
    }
    return null;
  }

  @Override
  public void refresh(boolean asynchronous) {

  }

  @Override
  public @Nullable VirtualFile refreshAndFindFileByPath(@NotNull String path) {
    return null;
  }

  @Override
  public void addVirtualFileListener(@NotNull VirtualFileListener listener) {

  }

  @Override
  public void removeVirtualFileListener(@NotNull VirtualFileListener listener) {

  }

  @Override
  protected void deleteFile(Object requestor, @NotNull VirtualFile vFile) throws IOException {

  }

  @Override
  protected void moveFile(Object requestor, @NotNull VirtualFile vFile, @NotNull VirtualFile newParent) throws IOException {

  }

  @Override
  protected void renameFile(Object requestor, @NotNull VirtualFile vFile, @NotNull String newName) throws IOException {

  }

  @Override
  protected @NotNull VirtualFile createChildFile(Object requestor, @NotNull VirtualFile vDir, @NotNull String fileName) throws IOException {
    return null;
  }

  @Override
  protected @NotNull VirtualFile createChildDirectory(Object requestor, @NotNull VirtualFile vDir, @NotNull String dirName)
    throws IOException {
    return null;
  }

  @Override
  protected @NotNull VirtualFile copyFile(Object requestor,
                                          @NotNull VirtualFile virtualFile,
                                          @NotNull VirtualFile newParent,
                                          @NotNull String copyName) throws IOException {
    return null;
  }

  @Override
  public boolean isReadOnly() {
    return true;
  }
  private void decodeManifest() {
    String manifestData = "";
    try {
      ZipFile file = new ZipFile(myApk.getPath());
      ZipEntry manifest = file.getEntry("AndroidManifest.xml");
      InputStream stream = file.getInputStream(manifest);
      byte[] data = new byte[stream.available()];
      int total = 0;
      while (stream.available() > 0)
        total += stream.read(data, total, stream.available());

      BinaryXMLParser parser = new BinaryXMLParser(root);
      manifestData = parser.parse(new ByteArrayInputStream(data)).toString();;

    } catch (IOException e ) {
      Logger.getInstance(APKFileSystem.class).error(e);
    }
    manifest = new ManifestFile(this, manifestData);
  }

  public VirtualFile getManifest() {
    if (manifest == null) {
      decodeManifest();
    }
    return manifest;
  }

}
