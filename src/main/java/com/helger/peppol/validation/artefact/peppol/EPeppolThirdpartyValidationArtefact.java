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
package com.helger.peppol.validation.artefact.peppol;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.validation.artefact.IValidationArtefact;
import com.helger.peppol.validation.domain.ThirdPartyKey;
import com.helger.peppol.validation.domain.ExtendedTransactionKey;
import com.helger.peppol.validation.domain.IBusinessSpecification;
import com.helger.peppol.validation.domain.ISpecificationTransaction;
import com.helger.peppol.validation.domain.TransactionKey;
import com.helger.peppol.validation.domain.peppol.PeppolTransactionKey;
import com.helger.ubl21.EUBL21DocumentType;

/**
 * This enumeration contains all the extended country specific OpenPEPPOL
 * Schematron artefacts I'm aware of. They are ordered ascending country, than
 * by BIS number, by transaction and finally by desired execution order.
 *
 * @author Philip Helger
 */
public enum EPeppolThirdpartyValidationArtefact implements IValidationArtefact
{
 INVOICE_AT_NAT ("Invoice-Thirdparty/ATNAT-UBL-T10.sch",
                 new ExtendedTransactionKey (PeppolTransactionKey.INVOICE_04_T10, ThirdPartyKey.AT),
                 "/ubl:Invoice/cac:AccountingCustomerParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'AT'"),
 INVOICE_AT_GOV ("Invoice-Thirdparty/ATGOV-UBL-T10.sch",
                 new ExtendedTransactionKey (PeppolTransactionKey.INVOICE_04_T10, ThirdPartyKey.AT_SECTOR),
                 "/ubl:Invoice/cac:AccountingCustomerParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'AT'"),

 BILLING_CREDIT_NOTE_AT_NAT ("Billing-Thirdparty/ATNAT-UBL-T14.sch",
                             new ExtendedTransactionKey (PeppolTransactionKey.BILLING_05_T14, ThirdPartyKey.AT),
                             "/ubl:CreditNote/cac:AccountingCustomerParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'AT'"),
 BILLING_CREDIT_NOTE_AT_GOV ("Billing-Thirdparty/ATGOV-UBL-T14.sch",
                             new ExtendedTransactionKey (PeppolTransactionKey.BILLING_05_T14, ThirdPartyKey.AT_SECTOR),
                             "/ubl:CreditNote/cac:AccountingCustomerParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'AT'");

  private final ClassPathResource m_aResource;
  private final ExtendedTransactionKey m_aExtendedTransactionKey;
  private String m_sPrerequisiteXPath;

  private EPeppolThirdpartyValidationArtefact (@Nonnull @Nonempty final String sPath,
                                               @Nonnull final ExtendedTransactionKey aExtendedTransactionKey,
                                               @Nullable final String sPrerequisiteXPath)
  {
    m_aResource = new ClassPathResource ("/peppol-rules/" + sPath);
    m_aExtendedTransactionKey = aExtendedTransactionKey;
    m_sPrerequisiteXPath = sPrerequisiteXPath;
  }

  @Nonnull
  public IReadableResource getSchematronResource ()
  {
    return m_aResource;
  }

  @Nonnull
  public ExtendedTransactionKey getExtendedTransactionKey ()
  {
    return m_aExtendedTransactionKey;
  }

  @Nonnull
  public TransactionKey getTransactionKey ()
  {
    return m_aExtendedTransactionKey.getTransactionKey ();
  }

  @Nonnull
  public IBusinessSpecification getBusinessSpecification ()
  {
    return m_aExtendedTransactionKey.getBusinessSpecification ();
  }

  @Nonnull
  public ISpecificationTransaction getTransaction ()
  {
    return m_aExtendedTransactionKey.getTransaction ();
  }

  @Nonnull
  public EUBL21DocumentType getUBLDocumentType ()
  {
    return m_aExtendedTransactionKey.getUBLDocumentType ();
  }

  public boolean isCountrySpecific ()
  {
    return m_aExtendedTransactionKey.isCountrySpecific ();
  }

  @Nonnull
  public Locale getCountryLocale ()
  {
    return m_aExtendedTransactionKey.getCountryLocale ();
  }

  @Nonnull
  @Nonempty
  public String getCountryCode ()
  {
    return m_aExtendedTransactionKey.getCountryCode ();
  }

  public boolean isSectorSpecific ()
  {
    return m_aExtendedTransactionKey.isSectorSpecific ();
  }

  @Nullable
  public String getPrerequisiteXPath ()
  {
    return m_sPrerequisiteXPath;
  }

  public boolean hasPrerequisiteXPath ()
  {
    return StringHelper.hasText (m_sPrerequisiteXPath);
  }

  /**
   * Get all validation artefacts matching the passed transaction key in the
   * correct execution order.
   *
   * @param aExtendedTransactionKey
   *        The extended transaction to search. May not be <code>null</code>.
   * @return A non-<code>null</code> list with all matching artefacts in the
   *         order they were defined. This list may be empty, if no matching
   *         artefact is present.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static List <EPeppolThirdpartyValidationArtefact> getAllMatchingValidationArtefacts (@Nonnull final ExtendedTransactionKey aExtendedTransactionKey)
  {
    ValueEnforcer.notNull (aExtendedTransactionKey, "ExtendedTransactionKey");

    final List <EPeppolThirdpartyValidationArtefact> ret = new ArrayList <EPeppolThirdpartyValidationArtefact> ();
    for (final EPeppolThirdpartyValidationArtefact e : values ())
      if (e.getExtendedTransactionKey ().equals (aExtendedTransactionKey))
        ret.add (e);
    return ret;
  }
}
