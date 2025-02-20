# Use a imagem oficial do Apache Tomcat como base
FROM tomcat:9.0.68

# Atualize as listas de pacotes e instale as localizações
RUN apt-get update && \
    apt-get install -y locales && \
    locale-gen pt_BR.UTF-8 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Defina a localização apropriada para pt_BR.UTF-8
ENV LANG pt_BR.UTF-8
ENV LANGUAGE pt_BR:pt
ENV LC_ALL pt_BR.UTF-8
ENV EMAIL_PASSWORD="bzaf moat gilv mirh"
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"
# Copie o arquivo WAR gerado para a pasta webapps do Tomcat
COPY target/adiblock-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/adiblock.war

# Exponha a porta padrão do Tomcat (8080)
EXPOSE 8080

# Defina o comando de entrada para iniciar o Tomcat
CMD ["catalina.sh", "run"]



