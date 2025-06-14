/**
 * ===============================================================
 * Nombre del archivo : UsuarioRepository.js
 * Autores            : Abraham Eduardo Quintana García, Cristian Eduardo Arreola Valenzuela
 * Descripción        : Maneja toda la logica necesaria para interactuar con la base de datos.
 * Última modificación: 2025-05-12
 * ===============================================================
 */

const pool = require('../config/dbConfig');
const bcrypt = require('bcrypt');

/**
 * Clase que contiene los metodos necesarios para interactuar con la base de datos.
 */
class UsuarioRepository {
    /**
     * Método que obtiene todos los usuarios.
     * @returns {Array<<Object>>} Arreglo que devuelve todos los usuarios.
     */
    async obtenerTodosLosUsuarios() {
        let connection;
        try {
            connection = await pool.getConnection();
            const [rows] = await connection.execute('SELECT id, usuario, correo, nombre, tipo FROM usuarios');
            return rows;
        } catch (error) {
            console.error('Error al obtener usuarios:', error);
            throw error;
        } finally {
            if (connection) connection.release();
        }
    }

    /**
     * Método que permite obtener un usuario por su nombre de usuario.
     * @param {string} usuario - Nombre de usuario.
     * @returns {Object} Objeto que contiene toda la informacion del usuario encontrado.
     */
    async obtenerUsuarioPorUsuario(usuario) {
        let connection;
        try {
            connection = await pool.getConnection();
            const [rows] = await connection.execute(
                `SELECT staff_id AS id, username AS usuario, email AS correo, passwd AS contrasena
                 FROM ost_staff WHERE username = ?`, 
                [usuario]
            );
            return rows.length > 0 ? rows[0] : null;
        } catch (error) {
            console.error('Error al obtener usuario de osTicket:', error);
            throw error;
        } finally {
            if (connection) connection.release();
        }
    }

    /**
     * Método que permite actualizar el password de un usuario.
     * @param {int} id - Id del usuario.
     * @param {string} nuevaContrasena - Nuevo password del usuario.
     * @returns {boolean} True si se actualizo con exito, False en caso contrario.
     */
    async actualizarContrasena(id, nuevaContrasena) {
        let connection;
        try {
            connection = await pool.getConnection();
            const hashedPass = await bcrypt.hash(nuevaContrasena, 10);
            await connection.execute('UPDATE usuarios SET contrasena = ? WHERE id = ?', [hashedPass, id]);
            return true;
        } catch (error) {
            console.error('Error al actualizar contraseña:', error);
            throw error;
        } finally {
            if (connection) connection.release();
        }
    }
}

module.exports = new UsuarioRepository();
