// Copyright 2000-2025 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.workshop.apk;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ManifestFile extends VirtualFile {

  private final APKFileSystem parent;
  private String manifestData;
  public ManifestFile(APKFileSystem fs, String data) {
    parent = fs;
    manifestData = data;
  }

  @Override
  public @NotNull String getName() {
    return "AndroidManifest.xml";
  }

  @Override
  public @NotNull VirtualFileSystem getFileSystem() {
    return parent;
  }

  @Override
  public @NotNull String getPath() {
    return parent.MANIFEST_PATH;
  }

  @Override
  public boolean isWritable() {
    return false;
  }

  @Override
  public boolean isDirectory() {
    return false;
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public VirtualFile getParent() {
    return null;
  }

  @Override
  public VirtualFile[] getChildren() {
    return new VirtualFile[0];
  }

  @Override
  public @NotNull OutputStream getOutputStream(Object requestor, long newModificationStamp, long newTimeStamp) throws IOException {
    return new ByteArrayOutputStream(0);
  }

  @Override
  public byte @NotNull [] contentsToByteArray() throws IOException {
    return manifestData.getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public long getTimeStamp() {
    return 0;
  }

  @Override
  public long getLength() {
    return 0;
  }

  @Override
  public void refresh(boolean asynchronous, boolean recursive, @Nullable Runnable postRunnable) {

  }

  @Override
  public @NotNull InputStream getInputStream() throws IOException {
    return null;
  }

  @Override
  public long getModificationStamp() {
    return 0;
  }
}
