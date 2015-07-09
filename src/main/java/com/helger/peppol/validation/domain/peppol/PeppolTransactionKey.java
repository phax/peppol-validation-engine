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
package com.helger.peppol.validation.domain.peppol;

import javax.annotation.concurrent.Immutable;

import com.helger.peppol.validation.domain.TransactionKey;

/**
 * An immutable pair of BIS of type {@link EPeppolBIS} and transaction of type
 * {@link EBII2Transaction}.
 *
 * @author Philip Helger
 */
@Immutable
public class PeppolTransactionKey
{
  // Predefined transaction keys, ordered by BIS and than by BII2 transaction
  public static final TransactionKey CATALOGUE_01_T19 = new TransactionKey (EPeppolBIS.CATALOGUE_01,
                                                                            EBII2Transaction.T19);
  public static final TransactionKey CATALOGUE_01_T58 = new TransactionKey (EPeppolBIS.CATALOGUE_01,
                                                                            EBII2Transaction.T58);
  public static final TransactionKey ORDER_03_T01 = new TransactionKey (EPeppolBIS.ORDER_03, EBII2Transaction.T01);
  public static final TransactionKey INVOICE_04_T10 = new TransactionKey (EPeppolBIS.INVOICE_04, EBII2Transaction.T10);
  public static final TransactionKey BILLING_05_T14 = new TransactionKey (EPeppolBIS.BILLING_05, EBII2Transaction.T14);
  public static final TransactionKey ORDERING_28_T01 = new TransactionKey (EPeppolBIS.ORDERING_28, EBII2Transaction.T01);
  public static final TransactionKey ORDERING_28_T76 = new TransactionKey (EPeppolBIS.ORDERING_28, EBII2Transaction.T76);
  public static final TransactionKey DESPATCH_ADVICE_30_T16 = new TransactionKey (EPeppolBIS.DESPATCH_ADVICE_30,
                                                                                  EBII2Transaction.T16);
  public static final TransactionKey MLR_36_T71 = new TransactionKey (EPeppolBIS.MLR_36, EBII2Transaction.T71);

  private PeppolTransactionKey ()
  {}
}