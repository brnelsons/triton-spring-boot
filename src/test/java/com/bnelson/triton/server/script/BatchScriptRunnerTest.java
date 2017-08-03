package com.bnelson.triton.server.script;

import com.bnelson.triton.server.data.OutputDelegate;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by brnel on 7/25/2017.
 */
public class BatchScriptRunnerTest {
    @Test
    public void runTestNoWait() {
        BatchScriptRunner runner = new BatchScriptRunner("echo abc");
        runner.run();
        OutputDelegate output = new OutputDelegate(100);
        output.addOutput(runner.getOutput());
        assertEquals("abc\n", output.read());
    }

    @Test
    public void runTestWait() {
        BatchScriptRunner runner = new BatchScriptRunner("echo abc 123");
        runner.run();
        OutputDelegate output = new OutputDelegate(100);
        output.addOutput(runner.getOutput());
        assertEquals("abc 123\n", output.read());
    }

    @Test
    public void runTestTaskTestExplorer() {
        BatchScriptRunner runner = new BatchScriptRunner(ScriptUtil.getIsProcessRunningString("explorer.exe"));
        runner.run();
        OutputDelegate output = new OutputDelegate(100);
        output.addOutput(runner.getOutput());
        assertTrue(ScriptUtil.isProcessRunningFromResult(output.read()));
    }

    @Test
    public void runTestTaskTestFalse() {
        BatchScriptRunner runner = new BatchScriptRunner(ScriptUtil.getIsProcessRunningString("notaprogram.exe"));
        runner.run();
        OutputDelegate output = new OutputDelegate(100);
        output.addOutput(runner.getOutput());
        assertFalse(ScriptUtil.isProcessRunningFromResult(output.read()));
    }
}