package kr.co_29cm.homework.support;

public enum OrderProcess {
    START, ORDER, QUIT, IO_EXCEPTION;

    public boolean isContinue() {
        return this.equals(START) || this.equals(ORDER);
    }

    public boolean isIoException() {
        return this.equals(IO_EXCEPTION);
    }
}
