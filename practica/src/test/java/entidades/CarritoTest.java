package entidades;

import org.example.entidades.Carrito;
import org.example.entidades.Categoria;
import org.example.entidades.ItemCarrito;
import org.example.entidades.Producto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarritoTest {
    private static Carrito carrito;
    private static Producto producto;

    /**
     * Esta es la configuracion inicial que se ejecuta una sola vez antes de todos los test, lo que setea el carrito y un producto de prueba.
     */
    @BeforeAll
    public static void setup() {
       carrito = Carrito.getInstance();
       producto = new Producto(1L, "Iphone 14", "Celular de alta gama de la marca Apple", new BigDecimal("1000.00"), Categoria.ELECTRONICO);
    }

    /**
     * Este metodo se ejecuta antes de cada test, limpia el carrito antes de cada prueba.
     */

    @BeforeEach
    public void resetCarrito() {
        carrito.getItems().clear();
    }

    /**
     * Test para comprobar que se puede agregar un item al carrito.
     */
    @Test
    public void agregarItemCarritoTest(){
        ItemCarrito itemCarrito = new ItemCarrito(1L,producto,2);
        int size_antes = carrito.getItems().size();
        carrito.agregarItem(itemCarrito);
        int size_despues = carrito.getItems().size();
        assert size_despues == size_antes + 1 : "No han aumentado los items de carrito ";
    }

    /**
     * Test para comprobar que se puede eliminar un item del carrito.
     */
    @Test
    public void eliminarItemCarritoTest(){
        ItemCarrito itemCarrito = new ItemCarrito(1L,producto,2);
        carrito.agregarItem(itemCarrito);

        int size_antes = carrito.getItems().size();
        carrito.elimarItem(itemCarrito.getId());
        int size_despues = carrito.getItems().size();


        if(size_despues == size_antes ){
            assert false : "No se ha elimando item del carrito";
        }else if(size_despues > size_antes ){
            assert true : "Se ha eliminado item del carrito correctamente";
        }
    }

    /**
     * Test para comprobar que se puede aumentar en 1(similar a los botones de aumentar y disminuir de los carritos) la cantidad de un item en el carrito.
     */
    @Test
    public void aumentarCantidadCarritoTest() {
        ItemCarrito itemCarrito = new ItemCarrito(1L, producto, 1);
        carrito.agregarItem(itemCarrito);
        assert  carrito.aumentarCantidadItem(itemCarrito.getId(),1) : "No se ha modificado la cantidad del item en el carrito";
        assertEquals(2, itemCarrito.getCantidad(), "La cantidad aumentada no es correcta");
    }

    /**
     * Test para comprobar que se puede disminuir en 1(similar a los botones de aumentar y disminuir de los carritos) la cantidad de un item en el carrito.
     */
    @Test
    public void disminuirCantidadCarritoTest() {
        ItemCarrito itemCarrito = new ItemCarrito(1L, producto, 3);
        carrito.agregarItem(itemCarrito);

        assert carrito.disminuirCantidadItem(itemCarrito.getId(),1) : "No se ha modificado la cantidad del item en el carrito";
        assertEquals(2, itemCarrito.getCantidad(), "La cantidad disminuida no es correcta");
    }

    /**
     * Test para comprobar que se puede asignar una cantidad especifica a un item en el carrito.
     */
    @Test
    public void asignarCantidadProductoTest() {
        ItemCarrito itemCarrito = new ItemCarrito(1L, producto, 2);
        carrito.agregarItem(itemCarrito);

        assert carrito.asignarCantidad(itemCarrito.getId(),5): "No se ha modificado la cantidad del item en el carrito";

        assertEquals(5, itemCarrito.getCantidad(), "La cantidad asignada no es correcta");
    }


    /**
     * Test para comprobar que se calcula correctamente el total del carrito.
     */
    @Test
    public void calcularTotalCarritoTest() {
        ItemCarrito itemCarrito1 = new ItemCarrito(1L, producto, 2);
        ItemCarrito itemCarrito2 = new ItemCarrito(2L, new Producto(2L, "Samsung Galaxy S21", "Celular de alta gama de la marca Samsung", new BigDecimal("800.00"), Categoria.ELECTRONICO), 1);
        ItemCarrito itemCarrito3 = new ItemCarrito(3L, new Producto(3L, "Mueble IKEA 3M", "Mueble de madera con una lognitud de 3 metros", new BigDecimal("500.00"), Categoria.HOGAR), 1);

        carrito.agregarItem(itemCarrito1);
        carrito.agregarItem(itemCarrito2);
        carrito.agregarItem(itemCarrito3);

        BigDecimal totalEsperado = itemCarrito1.getProducto().getPrecio().multiply(new BigDecimal(itemCarrito1.getCantidad()))
                .add(itemCarrito2.getProducto().getPrecio().multiply(new BigDecimal(itemCarrito2.getCantidad()))).add(itemCarrito3.getProducto().getPrecio().multiply(new BigDecimal(itemCarrito3.getCantidad())));

        assertEquals(totalEsperado, carrito.calcularTotal(), "El total del carrito no es correcto");
    }


}
