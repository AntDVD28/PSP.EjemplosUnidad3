# PSP.EjemplosUnidad3
Ejercicios de la asignatura de PSP (Programación de Servicios y Procesos) correspondientes a la unidad 3, "Programación en red".

Tres tipos de problemas:

- Servidor único, atiende a un solo cliente.
- Servidor secuencial, atiende a M clientes. Igual que el servidor único salvo dos diferencias:
  - existen varias instancias de clientes.
  - en el servidor tenemos un bucle para recibir varios clientes.
- Servidor concurrente que atiende a M clientes.
  - Normalmente tendremos un límite establecido de conexiones concurrentes.
  - Debemos de crear en el servidor un hilo por cada cliente, los guardaremos en una estructura de datos.
  - Los hilos podríamos implementarlos también como si fueran hilos. 


Pasos para implementar el <b>Servidor</b>:
1. Declaraciones e incialización de sockets y streams a null.
2. Gestión del puerto.
3. Instanciar el servidor.
4. Aceptar la petición conexión cliente.
5. Crear el flujo de entrada del cliente.
6. Crear el flujo de salida hacia el cliente.
7. Operamos.
8. Cerrar streams y sockets.

Pasos para implementar el <b>Cliente</b>:
1. Declaraciones e incialización de sockets y streams a null.
2. Gestión del puerto.
3. Instanciar el cliente <b>de forma controlada. El servidor debe de haber iniciado antes.</b>
4. Crear flujo de salida al servidor.
5. Crear flujo de entrada del cliente.
6. Operamos.
7. Cerramos streams y sockets.
