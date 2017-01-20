/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.bdve.spec;

import java.io.Serializable;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.MustImplementEqualsAndHashcode;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.name.IHasDisplayName;

/**
 * The base interface for business specifications (e.g. PEPPOL BIS)
 *
 * @author Philip Helger
 */
@MustImplementEqualsAndHashcode
public interface IBusinessSpecification extends IHasID <String>, IHasDisplayName, Serializable
{
  /**
   * @return The ID of the group maintaining the specification. E.g. "eu.peppol"
   *         for OpenPEPPOL.
   */
  @Nonnull
  @Nonempty
  String getGroupID ();

  /**
   * @return The ID of the specification itself.
   */
  @Nonnull
  @Nonempty
  String getSpecID ();

  /**
   * @return The overall ID of the business specification.
   */
  @Nonnull
  @Nonempty
  default String getID ()
  {
    return getGroupID () + ":" + getSpecID ();
  }
}
