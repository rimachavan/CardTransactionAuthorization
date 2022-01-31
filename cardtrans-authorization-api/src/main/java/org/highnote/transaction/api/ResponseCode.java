package org.highnote.transaction.api;

/**
 * Enum that helps to identify types of authorization responses.
 *
 *  <p>    OK - Authorization success </p>
 *  <p>    DE - Authorization declined </p>
 *  <p>    ER - Erroneous transaction message </p>
 *
 * @author rimachavan
 */
public enum ResponseCode {
    OK, DE, ER;
}
