# Examen Recuperatorio de Programación con Objetos II

---

> [!IMPORTANT]
> 👀 Leer todo antes de comenzar a resolver

## ⚙️ Datos necesarios y obligatorios a completar

> Borrar los datos de ejemplo y poner los datos reales

* **APELLIDO, NOMBRE**: Juárez Alfonso, Federico Lionel
* **COMISIÓN**: 3
* **DNI**: 47418550

> * Manuel Sadosky fue el impulsor de la llegada de Clementina, la primera computadora científica de Latinoamérica, y fundador en 1963 de la primera carrera de Computador Científico en la UBA.

---

## 📝 Consideraciones Iniciales y Criterio de evaluación

> Se evaluará cada solución prestando especial atención a:

- **Pautas obligatorias** (descritas abajo) correctamente cumplidas.
- **Entendimiento y correcta aplicación de los conceptos vistos en la cursada**: Solo los patrones de diseño vistos (Strategy | Template Method | Singleton), reificación, manejo de excepciones no chequeadas, test unitarios: **GWT** y **AAA**.
- **Prolijidad y legibilidad** del código presentado.
- Se realizará un control exhaustivo, incluyendo distintas herramientas de análisis estático de código para identificar posibles copias entre las soluciones entregadas, incluyendo el uso de IA.
- La solución debe aplicar patrones de diseño apropiados para la problemática planteada. **El uso inadecuado de patrones descalifica el examen automáticamente**.
- El código entregado debe tener los tests suficientes que garanticen el correcto funcionamiento de la solución propuesta por el alumno (*esperado 75%+*).
- No se aceptan entregas fuera de plazo ni que no estén correctamente subidas al repositorio de Gitea de la materia.
- Las entregas que tengan un solo commit o no reflejen el progreso del proceso de solución serán desaprobadas. **Se recomienda fuertemente realizar commits/push periódicamente y asegurarse de que impactaron correctamente en el repositorio remoto**.

## 📌 Pautas obligatorias para la entrega

> Utilizaremos un sistema de 3 'checkpoints', a saber:

- ⚠️ El código entregado debe compilar obligatoriamente. **Un parcial entregado cuyo código no compila queda desaprobado automáticamente**.
- **Checkpoint 1**: Push inicial. Clonar el repositorio remoto, modificar este archivo en la parte superior registrando **APELLIDO, NOMBRE**, **COMISIÓN** y **DNI** con sus datos, y hacer un primer push.
- **Checkpoint 2**: Push antes de realizar el primer test.
- **Checkpoint 3**: Push al final de la entrega, al terminar sus tests.

> [!NOTE]
> *Este es el mínimo requerido, pero puede (y es recomendable) hacer más pushes para estar cubiert@ ante cualquier imprevisto. El último push es el código que se corrige, pero se revisa todo el flujo de trabajo.*

> [!Warning]
> **ESTAS PAUTAS SON OBLIGATORIAS, DE NO CUMPLIRLAS, AUNQUE LA SOLUCIÓN ESTÉ PERFECTA, NO APROBARÁ EL EXAMEN** ‼️

---

# 💇 Sistema de Turnos y Fidelización de un Salón de Belleza

Se solicita desarrollar un sistema para gestionar los **turnos de un salón de belleza**. El sistema administra los servicios que se ofrecen, los clientes y el cálculo del importe de cada turno.

## ✂️ Servicios

El salón ofrece distintos tipos de servicio. Cada uno tiene un **precio base** y una **cantidad de cupos disponibles** para el día.

* **Corte de Cabello**: al confirmarse un turno de este tipo, se calcula el importe correspondiente, se emite un comprobante con el formato «TRN-COR-\<n\>», y el turno queda asentado como confirmado.
* **Coloración**: al confirmarse un turno de este tipo, se calcula el importe correspondiente, se emite un comprobante con el formato «TRN-COL-\<n\>», y el turno queda asentado como confirmado. Para este servicio en particular, el salón además deja registrado un aviso de retoque a los 30 días, algo que no ocurre con el resto de los servicios.
* **Tratamiento Capilar**: al confirmarse un turno de este tipo, se calcula el importe correspondiente, se emite un comprobante con el formato «TRN-TRA-\<n\>», y el turno queda asentado como confirmado. A diferencia del resto, este servicio puede reservarse por varias sesiones a la vez.

El precio base de un servicio no puede ser negativo.

Los cupos disponibles de un servicio se reducen cada vez que se confirma un turno.

> [!NOTE]
> El salón tiene planeado sumar nuevos servicios más adelante (por ejemplo, Manicura). Lo que se describe arriba para cada servicio existente debería seguir siendo válido para cualquier servicio que se agregue después.

## 👤 Clientes y categorías

Cada cliente posee un **nombre**, una **categoría** y un **historial de turnos**.

Las categorías disponibles son:

* **Ocasional**: no tiene descuento.
* **Frecuente**: tiene un descuento del 10% sobre el importe del turno.
* **VIP**: tiene un descuento del 20% sobre el importe del turno. Esta categoría está pensada para clientes que ya conocen el salón — no se le puede asignar a un cliente que todavía no tiene ningún turno en su historial.

El salón revisa periódicamente a sus clientes y puede recategorizarlos según su relación con el salón, de modo que un mismo cliente puede pasar, por ejemplo, de Ocasional a Frecuente sin dejar de ser el mismo cliente ni perder su historial.

El cliente debe poder consultar el **costo total de sus turnos**, calculado como la suma de los importes de todos los turnos de su historial.

## 🗓️ Turnos

Un turno se confirma para un cliente, un servicio y una cantidad de sesiones.

El turno registra:

* cliente;
* servicio;
* cantidad de sesiones;
* importe total.

Al confirmar el turno, se descuentan los cupos disponibles del servicio en la cantidad correspondiente.

El historial de turnos se conserva asociado al cliente.

Si se solicita el historial de un cliente que no posee turnos, se debe lanzar una `ClienteSinTurnosException`.

El sistema debe permitir consultar la **cantidad total de turnos confirmados** en todo el salón.

## 💰 Cálculo del importe

El importe de un turno se calcula a partir del precio base del servicio, la cantidad de sesiones, y la categoría del cliente que lo confirma.

Además de lo anterior, el salón cobra un **recargo fijo por uso de insumos**, que se suma al importe ya calculado. Este recargo es el mismo para cualquier turno que se confirme, y el encargado del salón puede actualizarlo cuando cambian los costos de los productos que usa — un cambio que, una vez hecho, debe reflejarse en cualquier turno que se confirme de ahí en adelante, sin excepción.

## ⚠️ Situaciones excepcionales

El sistema debe utilizar excepciones no chequeadas para las siguientes situaciones inválidas:

* **`CantidadInvalidaException`**: al intentar confirmar un turno con una cantidad de sesiones negativa o cero.
* **`PrecioNegativoException`**: al intentar instanciar un servicio con un precio base menor que cero.
* **`ClienteSinTurnosException`**: al solicitar el historial de turnos de un cliente que no posee ninguno.
* **`CategoriaNoAutorizadaException`**: al intentar asignarle la categoría VIP a un cliente que no tiene turnos previos en su historial.

## 🏗️ Restricciones de diseño

* Debe ser posible incorporar nuevos servicios sin modificar los servicios existentes.
* Debe ser posible incorporar nuevas categorías de cliente sin modificar las categorías existentes.
* Debe existir una separación clara entre clientes, categorías, servicios, turnos, y el recargo por insumos.
