/**
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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
package com.helger.phive.engine.vom;

import javax.annotation.Nullable;

import com.helger.commons.io.resource.IReadableResource;
import com.helger.phive.api.executorset.VESID;

/**
 * Resolver for VOM resources that are referenced from the VOM.
 *
 * @author Philip Helger
 */
public interface IVOMArtifactResolver
{
  /**
   * Find the artifact with the provided ID.
   *
   * @param aVESID
   *        The VESID to search. May be <code>null</code>.
   * @return The found resource or <code>null</code>.
   */
  @Nullable
  IReadableResource getArtifactOfID (@Nullable VESID aVESID);
}
