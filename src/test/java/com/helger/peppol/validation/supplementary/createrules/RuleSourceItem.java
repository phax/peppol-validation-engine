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
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.id.IHasID;
import com.helger.peppol.validation.supplementary.createrules.codelist.RuleSourceCodeList;
import com.helger.peppol.validation.supplementary.createrules.sch.RuleSourceBusinessRule;

public final class RuleSourceItem implements IHasID <String>
{
  private final File m_aRuleSrcDir;
  private final File m_aRuleDstDir;
  private final String m_sID;
  private final List <RuleSourceCodeList> m_aCodeLists = new ArrayList <RuleSourceCodeList> ();
  private final List <RuleSourceBusinessRule> m_aBusinessRules = new ArrayList <RuleSourceBusinessRule> ();

  public RuleSourceItem (@Nonnull final File aRuleSrcDir,
                         @Nonnull final File aRuleDstDir,
                         @Nonnull @Nonempty final String sID)
  {
    ValueEnforcer.isTrue (aRuleSrcDir.isDirectory (), aRuleSrcDir + " is not a directory!");
    ValueEnforcer.isTrue (aRuleDstDir.isDirectory (), aRuleDstDir + " is not a directory!");
    m_aRuleSrcDir = aRuleSrcDir;
    m_aRuleDstDir = aRuleDstDir;
    m_sID = sID.toUpperCase (Locale.US);
  }

  @Nonnull
  public File getOutputCodeListDirectory ()
  {
    return new File (m_aRuleDstDir, "codelist");
  }

  @Nonnull
  public File getOutputSchematronDirectory ()
  {
    return m_aRuleDstDir;
  }

  @Nonnull
  public RuleSourceItem addCodeList (@Nonnull @Nonempty final String sSourceFilename)
  {
    m_aCodeLists.add (new RuleSourceCodeList (new File (m_aRuleSrcDir, sSourceFilename),
                                              getOutputCodeListDirectory (),
                                              getOutputSchematronDirectory (),
                                              m_sID));
    return this;
  }

  @Nonnull
  public RuleSourceItem addBussinessRule (@Nonnull @Nonempty final String sSourceFilename)
  {
    return addBussinessRule (sSourceFilename, null);
  }

  @Nonnull
  public RuleSourceItem addBussinessRule (@Nonnull @Nonempty final String sSourceFilename,
                                          @Nullable final String sCodeListTransaction)
  {
    m_aBusinessRules.add (new RuleSourceBusinessRule (new File (m_aRuleSrcDir, sSourceFilename),
                                                      getOutputSchematronDirectory (),
                                                      m_sID,
                                                      sCodeListTransaction));
    return this;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <RuleSourceCodeList> getAllCodeLists ()
  {
    return CollectionHelper.newList (m_aCodeLists);
  }

  @Nonnull
  @ReturnsMutableCopy
  public List <RuleSourceBusinessRule> getAllBusinessRules ()
  {
    return CollectionHelper.newList (m_aBusinessRules);
  }
}