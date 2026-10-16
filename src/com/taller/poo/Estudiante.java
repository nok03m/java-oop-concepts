package com.taller.poo;

/**
 * Represents an academic student entity in the enrollment and grading system.
 * Demonstrates basic encapsulation, member variable declaration without inline initialization,
 * and string manipulation using StringBuilder and formatted strings.
 */
public class Estudiante {

    // Student identification code or enrollment number
    private String identificacion;

    // Full name of the student
    private String nombreCompleto;

    // Grade point average (GPA)
    private double promedioCalificaciones;

    // Enrollment status (true if actively enrolled, false otherwise)
    private boolean estaMatriculado;

    /**
     * Default constructor initializing default baseline values.
     */
    public Estudiante() {
        this.identificacion = "N/A";
        this.nombreCompleto = "Sin Nombre";
        this.promedioCalificaciones = 0.0;
        this.estaMatriculado = false;
    }

    /**
     * Parameterized constructor.
     * State initialization is delegated exclusively to this constructor.
     *
     * @param identificacion         Student identification code.
     * @param nombreCompleto         Student's full name.
     * @param promedioCalificaciones Cumulative grade point average.
     * @param estaMatriculado        Active enrollment status.
     */
    public Estudiante(String identificacion, String nombreCompleto, double promedioCalificaciones, boolean estaMatriculado) {
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.promedioCalificaciones = promedioCalificaciones;
        this.estaMatriculado = estaMatriculado;
    }

    /**
     * Builds and returns a formatted academic report for the student.
     * Uses StringBuilder and String.format() with specifiers %s, %.2f, and %b.
     * In accordance with separation of concerns, this method returns the String
     * rather than directly printing to standard output.
     *
     * @return Formatted academic report string.
     */
    public String obtenerReporteAcademico() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ID: %s | Student: %s | GPA: %.2f | Enrolled: %b",
                this.identificacion,
                this.nombreCompleto,
                this.promedioCalificaciones,
                this.estaMatriculado));
        return sb.toString();
    }

    // --- Getters and Setters ---

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public double getPromedioCalificaciones() {
        return promedioCalificaciones;
    }

    public void setPromedioCalificaciones(double promedioCalificaciones) {
        this.promedioCalificaciones = promedioCalificaciones;
    }

    public boolean isEstaMatriculado() {
        return estaMatriculado;
    }

    public void setEstaMatriculado(boolean estaMatriculado) {
        this.estaMatriculado = estaMatriculado;
    }
}
