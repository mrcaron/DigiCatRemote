/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.payload;

/**
 *
 * @author developer
 */
public class PresetReportPayload extends SequencePayload {
    public int getPresetNum() { return this.data.get(0); }
    public int getInputForOutput(int output) { return this.data.get(output); }
}
