// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.ide.projectView.impl.nodes;

import com.intellij.ide.IdeBundle;
import com.intellij.ide.projectView.*;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.backend.workspace.WorkspaceModel;
import com.intellij.platform.workspace.storage.ImmutableEntityStorage;
import com.intellij.platform.workspace.storage.WorkspaceEntity;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.util.PlatformIcons;
import com.intellij.util.containers.ContainerUtil;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MyTestNode extends ProjectViewNode<String> {
  private static final Logger LOG = Logger.getInstance(MyTestNode.class);

  public MyTestNode(@NotNull Project project, ViewSettings viewSettings) {
    super(project, "External Libraries", viewSettings);
  }

  @Override
  public boolean isAlwaysShowPlus() {
    return true;
  }

  @Override
  public boolean isIncludedInExpandAll() {
    return false;
  }

  @Override
  public boolean contains(@NotNull VirtualFile file) {
    Project project = Objects.requireNonNull(getProject());
    ProjectFileIndex index = ProjectFileIndex.getInstance(project);
    return index.isInLibrary(file) && someChildContainsFile(file, false);
  }

  @Override
  public @NotNull Collection<? extends AbstractTreeNode<?>> getChildren() {
    Project project = Objects.requireNonNull(getProject());
    Module[] modules = ModuleManager.getInstance(project).getModules();
    return getChildren(project, modules);
  }

  protected @NotNull List<AbstractTreeNode<?>> getChildren(@NotNull Project project, Module[] modules) {
    List<AbstractTreeNode<?>> children = new ArrayList<>();
    children.add(new PsiFileNode(this.myProject, PsiFileFactory.getInstance(myProject).createFileFromText("test.txt", "test"), this.getSettings()));
    return children;
  }

  @Override
  protected void update(@NotNull PresentationData presentation) {
    presentation.setPresentableText("Test Node");
    presentation.setIcon(PlatformIcons.FOLDER_ICON);
  }

  @Override
  public @NotNull NodeSortOrder getSortOrder(@NotNull NodeSortSettings settings) {
    return NodeSortOrder.LIBRARY_ROOT;
  }
}