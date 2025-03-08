// Copyright 2000-2025 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.ide.projectView.impl.nodes;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApkListNode extends ProjectViewNode<String> {
  /**
   * Creates an instance of the project view node.
   *
   * @param project      the project containing the node.
   * @param s            the object (for example, a PSI element) represented by the project view node
   * @param viewSettings the settings of the project view.
   */
  protected ApkListNode(Project project,
                        ViewSettings viewSettings) {
    super(project, "APKS", viewSettings);
  }

  @Override
  public boolean contains(@NotNull VirtualFile file) {
    return false;
  }

  @Override
  public @Unmodifiable @NotNull Collection<? extends AbstractTreeNode<?>> getChildren() {
    List<AbstractTreeNode<?>> children = new ArrayList<>();
    if (myProject == null)
      return  children;
    Collection<VirtualFile> files = FilenameIndex.getAllFilesByExt(myProject, "apk");

    for (VirtualFile file : files) {
      PsiFile psiFile = PsiManager.getInstance(myProject).findFile(file);
      if (psiFile != null)
        children.add(new ApkViewNode(myProject, psiFile, getSettings()));
    }
    return  children;
  }

  @Override
  protected void update(@NotNull PresentationData presentation) {
    presentation.setPresentableText("APKs");
    presentation.setIcon(PlatformIcons.FOLDER_ICON);
  }
}
