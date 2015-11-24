package com.alankhazam.lazysusan;

/**
 * Created by Shubham on 11/23/2015.
 */
public abstract class Result<T,F> {
    public static class OK<T, F> extends Result<T, F>{
        public final T data;
        public OK(T data){
            this.data = data;
        }
    }
    public static class Error<T, F> extends Result<T, F>{
        public final F error;
        public Error(F error){
            this.error = error;
        }
    }
}
