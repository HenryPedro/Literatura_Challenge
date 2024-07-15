package br.com.alura.literatura.service;

public interface IConverterDados {
    <T> T obterDados(String json, Class<T> classe);
}
