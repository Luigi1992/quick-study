package com.gcw_rome_2014.saveme.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

/**
 * Created by Luigi on 04/01/2015.
 *
 */
public class AndroidAccount {

    //Application Context
    public Context context;

    public AndroidAccount(Context context) {
        this.context = context;
    }

    /**
     * Return an array containing all accounts on the phone.
     */
    public Account[] getAccounts() {
        AccountManager m = AccountManager.get(context);
        android.accounts.Account[] accounts = m.getAccounts();

        //TODO: Remove later.
        for (android.accounts.Account a : accounts) {
            System.out.println("Name: " + a.name);
            System.out.println("Type: " + a.type);
        }

        return accounts;
    }
}
