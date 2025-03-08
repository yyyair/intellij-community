// Copyright 2000-2025 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.workshop.apk;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class APKFile extends VirtualFile {
  @Override
  public @NotNull String getName() {
    return "";
  }

  @Override
  public @NotNull VirtualFileSystem getFileSystem() {
    return null;
  }

  @Override
  public @NotNull String getPath() {
    return "";
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
    return false;
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
    return null;
  }

  @Override
  public byte @NotNull [] contentsToByteArray() throws IOException {
    return new byte[0];
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
}
