# 🍞 El Buen Pan - POS System

Sistema de Punto de Venta (POS) profesional desarrollado en **Java**, diseñado específicamente para la gestión integral y optimización operativa de panaderías locales.

## 🛠️ Stack Tecnológico
* **Lenguaje:** Java SE (Swing para la interfaz gráfica).
* **Persistencia:** PostgreSQL.
* **Paradigma:** Programación Orientada a Objetos (POO) con modularización de estilos y lógica de negocio.
* **Entorno:** Desarrollado y optimizado bajo **Arch Linux**.

## 🚀 Funcionalidades Clave
* **Módulo de Ventas Ágil:** Interfaz optimizada con selección de productos mediante imágenes y teclado numérico compacto.
* **Control de Inventario:** Gestión de existencias en tiempo real con filtrado dinámico y alertas visuales.
* **Sistema de Encargos:** Registro de pedidos especiales, gestión de anticipos y seguimiento de fechas de entrega.
* **Corte de Caja y Merma:** Generación automática de reportes de cierre diario y registro de desperdicios para auditoría.

## 🎨 Arquitectura de UI/UX
* **Legibilidad Extrema:** Implementación de fuentes escaladas (22pt - 28pt) para facilitar la operación en entornos de alta transaccionalidad.
* **Consistencia Visual:** Estilización global mediante clases personalizadas para el renderizado de tablas (`TableCellRenderer`) y configuración unificada de `UIManager`.
* **Diseño Moderno:** Interfaz basada en una paleta de colores tierra y acero, con tablas tipo cebra (sin líneas verticales) para reducir la fatiga visual.
* **Prevención de Errores:** Validaciones dinámicas de campos (estados Rojo/Verde) y diálogos de confirmación para acciones críticas como el vaciado de venta o cierre de sesión.

## 📂 Estructura del Proyecto
* `/src/Clases`: Lógica de negocio, configuradores de estilo y renderizadores personalizados.
* `/src/Formularios`: Interfaces de usuario (Login, Menú Principal, Inventario).
* `/assets`: Iconos, recursos gráficos y capturas de pantalla del sistema.
