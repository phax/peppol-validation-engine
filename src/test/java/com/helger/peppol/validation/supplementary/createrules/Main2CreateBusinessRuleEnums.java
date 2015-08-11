package com.helger.peppol.validation.supplementary.createrules;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.io.file.iterate.FileSystemIterator;
import com.helger.commons.regex.RegExHelper;
import com.helger.peppol.validation.domain.peppol.EBII2Transaction;

/**
 * Create the content of the {@link ERuleSource} enum. Must be run AFTER
 * {@link Main1CreateCodeLists} was run!
 *
 * @author Philip Helger
 */
public final class Main2CreateBusinessRuleEnums
{
  private static final String KEY_CODELISTS = "CODELISTS";

  private static final class MyMap extends TreeMap <String, String>
  {
    @Override
    public String put (@Nonnull final String sKey, @Nonnull final String sValue)
    {
      final String sOld = super.put (sKey, sValue);
      if (sOld != null)
        throw new IllegalArgumentException ("A value for the key '" + sKey + "' was already contained - duplicate!");
      return sOld;
    }
  }

  @Nonnull
  @ReturnsMutableCopy
  private static MyMap _getBIIRuleVersions ()
  {
    final MyMap ret = new MyMap ();
    for (final File aFile : new FileSystemIterator ("src/test/resources/rule-source/biirules/businessrules"))
      if (aFile.isFile () && aFile.getName ().endsWith (".ods"))
      {
        String [] aMatches = RegExHelper.getAllMatchingGroupValues ("bii2rules-(T[0-9]+)-BusinessRules-(v[0-9]+)\\.ods",
                                                                    aFile.getName ());
        if (aMatches != null)
          ret.put (aMatches[0], aMatches[1]);
        else
        {
          aMatches = RegExHelper.getAllMatchingGroupValues ("bii2rules-CodeLists-(v[0-9]+)\\.ods", aFile.getName ());
          if (aMatches != null)
            ret.put (KEY_CODELISTS, aMatches[0]);
          else
            throw new IllegalStateException ();
        }
      }
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  private static MyMap _getOpenPEPPOLRuleVersions ()
  {
    final MyMap ret = new MyMap ();
    for (final File aFile : new FileSystemIterator ("src/test/resources/rule-source/peppol/businessrules"))
      if (aFile.isFile () && aFile.getName ().endsWith (".ods"))
      {
        String [] aMatches = RegExHelper.getAllMatchingGroupValues ("OpenPEPPOL-(T[0-9]+)-BusinessRules-(v[0-9]+)\\.ods",
                                                                    aFile.getName ());
        if (aMatches != null)
          ret.put (aMatches[0], aMatches[1]);
        else
        {
          aMatches = RegExHelper.getAllMatchingGroupValues ("OpenPEPPOL-CodeLists-(v[0-9]+)\\.ods", aFile.getName ());
          if (aMatches != null)
            ret.put (KEY_CODELISTS, aMatches[0]);
          else
            throw new IllegalStateException ();
        }
      }
    return ret;
  }

  private static String _getVersion (@Nonnull final MyMap aMap, @Nonnull final String sKey)
  {
    final String sValue = aMap.get (sKey);
    return sValue == null ? "null" : '"' + sValue + '"';
  }

  @Nonnull
  private static File _getBIICOREFile (@Nonnull final String sTransactionKey)
  {
    return new File ("src/test/resources/rule-source/biicore/BIICORE-UBL-" + sTransactionKey + "-V1.0.sch");
  }

  private static boolean _hasCodeList (@Nonnull final String sPrefix, @Nonnull final String sTransactionKey)
  {
    return new File ("src/test/resources/codelist-generated/cva/" + sPrefix + "-" + sTransactionKey + ".cva").exists ();
  }

  public static void main (final String [] args)
  {
    final MyMap aBIIRules = _getBIIRuleVersions ();
    final MyMap aOpenPEPPOLRules = _getOpenPEPPOLRuleVersions ();

    final List <String> aKeys = new ArrayList <String> ();
    for (final EBII2Transaction eTransaction : EBII2Transaction.values ())
      aKeys.add (eTransaction.getTransactionKeyShort ());
    aKeys.add (KEY_CODELISTS);

    for (final String sKey : aKeys)
    {
      final File aBIICoreFile = _getBIICOREFile (sKey);
      System.out.println (sKey +
                          " (" +
                          (aBIICoreFile.exists () ? '"' + aBIICoreFile.getName () + '"' : "null") +
                          ", " +
                          _getVersion (aBIIRules, sKey) +
                          ", " +
                          _hasCodeList ("BIIRULES", sKey) +
                          ", " +
                          _getVersion (aOpenPEPPOLRules, sKey) +
                          ", " +
                          _hasCodeList ("OPENPEPPOL", sKey) +
                          "),");
    }
  }
}