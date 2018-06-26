package cn.codeleven.response;

import java.io.*;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/21
 */
public class PrintWriter extends java.io.PrintWriter {
    public PrintWriter(Writer out) {
        super(out);
    }

    public PrintWriter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public PrintWriter(OutputStream out) {
        super(out);
    }

    public PrintWriter(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public PrintWriter(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    public PrintWriter(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, csn);
    }

    public PrintWriter(File file) throws FileNotFoundException {
        super(file);
    }

    public PrintWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(file, csn);
    }

    @Override
    public void print(boolean b) {
        super.print(b);
        super.flush();
    }

    @Override
    public void print(char c) {
        super.print(c);
        super.flush();

    }

    @Override
    public void print(int i) {
        super.print(i);
        super.flush();

    }

    @Override
    public void print(long l) {
        super.print(l);
        super.flush();

    }

    @Override
    public void print(float f) {
        super.print(f);
        super.flush();

    }

    @Override
    public void print(double d) {
        super.print(d);
        super.flush();

    }

    @Override
    public void print(char[] s) {
        super.print(s);
        super.flush();

    }

    @Override
    public void print(String s) {
        super.print(s);
        super.flush();

    }

    @Override
    public void print(Object obj) {
        super.print(obj);
        super.flush();

    }

    @Override
    public void println() {
        super.println();
        super.flush();

    }

    @Override
    public void println(boolean x) {
        super.println(x);
        super.flush();

    }

    @Override
    public void println(char x) {
        super.println(x);
        super.flush();

    }

    @Override
    public void println(int x) {
        super.println(x);
        super.flush();

    }

    @Override
    public void println(long x) {
        super.println(x);
        super.flush();

    }

    @Override
    public void println(float x) {
        super.println(x);
        super.flush();

    }

    @Override
    public void println(double x) {
        super.println(x);
        super.flush();

    }


    @Override
    public void println(char[] x) {
        super.println(x);
        super.flush();

    }

    @Override
    public void println(String x) {
        super.println(x);
        super.flush();

    }

    @Override
    public void println(Object x) {
        super.println(x);
        super.flush();

    }
}
