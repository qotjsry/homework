package kr.co_29cm.homework.domain;

public enum OrderProcess {
    START, ORDER, QUIT;

    public boolean isContinue() {
        return this.equals(START) || this.equals(ORDER);
    }
}
