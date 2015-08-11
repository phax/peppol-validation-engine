/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.peppol.validation.supplementary.createrules;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.io.file.FileOperations;
import com.helger.peppol.validation.domain.peppol.EBII2Transaction;

public final class RuleSourceGroup
{
  private final File m_aRuleSrcDir;
  private final File m_aRuleDstDir;
  private final ESyntaxBinding m_eBinding;
  private final EBII2Transaction m_eTransaction;
  // Status vars
  private final List <RuleSourceItem> m_aItems = new ArrayList <RuleSourceItem> ();

  /**
   * @param aRuleSrcDir
   *        Rule source directory. Must exist.
   * @param aRuleDstDir
   *        Rule destination directory. Must exist.
   * @param eBinding
   *        <code>null</code> if all syntax bindings should be processed, the
   *        chosen syntax otherwise.
   * @param eTransaction
   *        Transaction to use. May not be <code>null</code>.
   */
  public RuleSourceGroup (@Nonnull final File aRuleSrcDir,
                          @Nonnull final File aRuleDstDir,
                          @Nullable final ESyntaxBinding eBinding,
                          @Nonnull final EBII2Transaction eTransaction)
  {
    ValueEnforcer.isTrue (aRuleSrcDir.isDirectory (), aRuleSrcDir + " is not a directory!");
    FileOperations.createDirIfNotExisting (aRuleDstDir);
    ValueEnforcer.isTrue (aRuleDstDir.isDirectory (), aRuleDstDir + " is not a directory!");
    m_aRuleSrcDir = aRuleSrcDir;
    m_aRuleDstDir = aRuleDstDir;
    m_eBinding = eBinding;
    m_eTransaction = eTransaction;
  }

  @Nonnull
  public File getOutputSchematronDirectory ()
  {
    return m_aRuleDstDir;
  }

  @Nonnull
  public RuleSourceItem addItem (@Nonnull @Nonempty final String sID)
  {
    final RuleSourceItem aItem = new RuleSourceItem (m_aRuleSrcDir, m_aRuleDstDir, sID, m_eBinding, m_eTransaction);
    m_aItems.add (aItem);
    return aItem;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <RuleSourceItem> getAllItems ()
  {
    return CollectionHelper.newList (m_aItems);
  }

  @Nonnull
  @Nonempty
  private String _getBIICOREFilename ()
  {
    return "BIICORE-" + m_eBinding.getID () + "-" + m_eTransaction.getTransactionKeyShort () + "-V1.0.sch";
  }

  @Nonnull
  public File getBIICORESrcFile ()
  {
    return new File (m_aRuleSrcDir, "biicore/" + _getBIICOREFilename ());
  }

  @Nonnull
  public File getBIICOREDstFile ()
  {
    return new File (m_aRuleDstDir, _getBIICOREFilename ());
  }
}
