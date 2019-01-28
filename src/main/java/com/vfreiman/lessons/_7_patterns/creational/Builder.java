package com.vfreiman.lessons._7_patterns.creational;

public class Builder {

    private String field;
    private int field2;
    public Builder setField(String field) {
        this.field = field;
        return this;
    }
    public Builder setField2(int field2) {
        this.field2 = field2;
        return this;
    }
    public MyClass create() {
        return new MyClass(this);
    }

    public String getField() {
        return field;
    }

    public int getField2() {
        return field2;
    }

    private class MyClass {
        private String field;
        private int field2;
        private MyClass(Builder builder) {
            this.field = builder.getField();
            this.field2 = builder.getField2();
        }
        public String getField() {
            return field;
        }
        public int getField2() {
            return field2;
        }

        @Override
        public String toString() {
            return "MyClass{" +
                    "field='" + field + '\'' +
                    ", field2=" + field2 +
                    '}';
        }
    }

    public static void main(String[] args) {
        MyClass myClass = new Builder().setField("asdf").setField2(12345).create();
        System.out.println(myClass);
    }
}
