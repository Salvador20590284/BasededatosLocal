package com.example.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText Clave;
    EditText Nombre;
    EditText Carrera;
    EditText Semestre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Clave = findViewById(R.id.Clave);
        Nombre = findViewById(R.id.Nombre);
        Carrera = findViewById(R.id.Carrera);
        Semestre = findViewById(R.id.Semestre);

    }

    public void altas (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);//cambiar nombre dependiendo el proyecto que manejes
        SQLiteDatabase bd = admin.getWritableDatabase();
        String Cv = Clave.getText().toString();
        String Nm = Nombre.getText().toString();
        String Cr = Carrera.getText().toString();
        String Sm = Semestre.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("clave", Cv);
        registro.put("nombre", Nm);
        registro.put("carrera", Cr);
        registro.put("semestre", Sm);
        // los inserto en la base de datos
        bd.insert("usuario", null, registro);
        bd.close();
        Clave.setText("");
        Nombre.setText("");
        Carrera.setText("");
        Semestre.setText("");
        Toast.makeText(this, "La alta de la tabla se realizo", Toast.LENGTH_SHORT).show();
    }

    // Hacemos búsqueda de usuario por DNI
    public void consulta (View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String Cv = Clave.getText().toString();
        Cursor fila = bd.rawQuery( "select Nombre, Carrera, Semestre from usuario where Clave = " + Cv, null);
        if (fila.moveToFirst()) {
            Nombre.setText(fila.getString(0));
            Carrera.setText(fila.getString(1));
            Semestre.setText(fila.getString(2));
        } else
            Toast.makeText(this, "No existe ningún usuario con ese dni", Toast.LENGTH_SHORT).show();
        bd.close();
    }

    /* Método para dar de baja al usuario insertado*/
    public void baja(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String Cv = Clave.getText().toString();
        // aquí borro la base de datos del usuario por el dni
        int cant = bd.delete("usuario", "Clave=" + Cv, null);
        bd.close();
        Clave.setText(""); Nombre.setText(""); Carrera.setText(""); Semestre.setText("");
        if (cant == 1)
            Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe usuario", Toast.LENGTH_SHORT).show();
    }

    // Método para modificar la información del usuario
    public void modificacion(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String Cv = Clave.getText().toString();
        String Nm = Nombre.getText().toString();
        String Cr = Carrera.getText().toString();
        String Sm = Semestre.getText().toString();
        ContentValues registro = new ContentValues();
        // actualizamos con los nuevos datos, la información cambiada
        registro.put("Nombre", Nm);
        registro.put("Carrera", Cr);
        registro.put("Semestre", Sm);
        int cant = bd.update("usuario", registro, "Clave=" + Cv, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "Datos modificados con éxito", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe usuario", Toast.LENGTH_SHORT).show();
    }

}