/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.Categoria;
import Model.Gasto;
import Model.Ingreso;
import Model.Meta;
import Model.Presupuesto;
import Util.Response;
/**
 *
 * @author Camil
 */
public class ChatbotService {
    
    private PresupuestoService presupuestoService;
    private IngresoService ingresoService;
    private GastoService gastoService;
    private MetaService metaService;
    private CategoriaService categoriaService;

    public ChatbotService() {

        presupuestoService = new PresupuestoService();
        ingresoService = new IngresoService();
        gastoService = new GastoService();
        metaService = new MetaService();
        categoriaService = new CategoriaService();
    }

    public String procesarMensaje(String mensaje) {

        mensaje = mensaje.toLowerCase().trim();

        try {

            // =========================
            // CONSULTAR PRESUPUESTO
            // =========================
            if (mensaje.contains("presupuesto")) {

                Response<Presupuesto> respuesta =
                        presupuestoService.obtenerTodos();

                if (respuesta.getLista() != null
                        && !respuesta.getLista().isEmpty()) {

                    Presupuesto p = respuesta.getLista().get(0);

                    return "Presupuesto: "
                            + p.getNombre()
                            + "\nMonto inicial: $" + p.getMontoInicial()
                            + "\nMonto actual: $" + p.getMontoActual();
                }

                return "No hay presupuestos registrados.";
            }

            // =========================
            // CONSULTAR INGRESOS
            // =========================
            if (mensaje.contains("ingresos")
                    || mensaje.contains("mostrar ingresos")) {

                Response<Ingreso> respuesta =
                        ingresoService.obtenerTodos();

                if (respuesta.getLista() == null
                        || respuesta.getLista().isEmpty()) {

                    return "No hay ingresos registrados.";
                }

                String texto = "Ingresos registrados:\n";

                double total = 0;

                for (Ingreso i : respuesta.getLista()) {

                    texto += "- "
                            + i.getFuente()
                            + " : $"
                            + i.getMonto()
                            + "\n";

                    total += i.getMonto();
                }

                texto += "\nTotal ingresos: $" + total;

                return texto;
            }

            // =========================
            // CONSULTAR GASTOS
            // =========================
            if (mensaje.contains("gastos")
                    || mensaje.contains("mostrar gastos")) {

                Response<Gasto> respuesta =
                        gastoService.obtenerTodos();

                if (respuesta.getLista() == null
                        || respuesta.getLista().isEmpty()) {

                    return "No hay gastos registrados.";
                }

                String texto = "Gastos registrados:\n";

                double total = 0;

                for (Gasto g : respuesta.getLista()) {

                    texto += "- "
                            + g.getDescripcion()
                            + " : $"
                            + g.getMonto()
                            + "\n";

                    total += g.getMonto();
                }

                texto += "\nTotal gastos: $" + total;

                return texto;
            }

            // =========================
            // CREAR META
            // =========================
            if (mensaje.startsWith("crear meta")) {

                String[] partes = mensaje.split(" ");

                if (partes.length < 4) {
                    return "Formato: crear meta NombreMeta Valor";
                }

                String nombreMeta = partes[2];

                double montoMeta =
                        Double.parseDouble(partes[3]);

                Meta meta = new Meta(
                        0,
                        nombreMeta,
                        montoMeta,
                        0,
                        java.time.LocalDate.now().plusMonths(6),
                        1
                );

                Response<Meta> respuesta =
                        metaService.insertar(meta);

                return respuesta.getMensaje();
            }

            // =========================
            // MOSTRAR METAS
            // =========================
            if (mensaje.contains("metas")
                    || mensaje.contains("mostrar metas")) {

                Response<Meta> respuesta =
                        metaService.obtenerTodos();

                if (respuesta.getLista() == null
                        || respuesta.getLista().isEmpty()) {

                    return "No hay metas registradas.";
                }

                String texto = "Metas registradas:\n";

                for (Meta m : respuesta.getLista()) {

                    texto += "- "
                            + m.getNombreMeta()
                            + " | Objetivo: $"
                            + m.getMontoMeta()
                            + "\n";
                }

                return texto;
            }

            // =========================
            // CREAR CATEGORIA
            // =========================
            if (mensaje.startsWith("crear categoria")) {

                String[] partes = mensaje.split(" ");

                if (partes.length < 4) {
                    return "Formato: crear categoria Nombre Tipo";
                }

                String nombre = partes[2];

                String tipo = partes[3];

                Categoria categoria =
                        new Categoria(
                                0,
                                nombre,
                                tipo
                        );

                Response<Categoria> respuesta =
                        categoriaService.insertar(categoria);

                return respuesta.getMensaje();
            }

            // =========================
            // TOTAL GASTOS
            // =========================
            if (mensaje.contains("total gastos")
                    || mensaje.contains("cuanto he gastado")
                    || mensaje.contains("gastos totales")) {

                double total =
                        gastoService.calcularTotalGastos();

                return "Tus gastos totales son: $" + total;
            }

            // =========================
            // TOTAL INGRESOS
            // =========================
            if (mensaje.contains("total ingresos")
                    || mensaje.contains("cuanto he ingresado")
                    || mensaje.contains("ingresos totales")) {

                double total =
                        ingresoService.calcularTotalIngresos();

                return "Tus ingresos totales son: $" + total;
            }

            // =========================
            // AHORRO
            // =========================
            if (mensaje.contains("ahorro")
                    || mensaje.contains("cuanto he ahorrado")
                    || mensaje.contains("saldo actual")) {

                double ingresos =
                        ingresoService.calcularTotalIngresos();

                double gastos =
                        gastoService.calcularTotalGastos();

                double ahorro =
                        ingresos - gastos;

                return "Resumen financiero:"
                        + "\nIngresos: $" + ingresos
                        + "\nGastos: $" + gastos
                        + "\nAhorro actual: $" + ahorro;
            }

            return "No entendí la solicitud.";

        } catch (Exception e) {

            return "Error: " + e.getMessage();
        }
    }
}
