/**
 * @file app/register/page.tsx
 * @description Pantalla de registro de usuarios conectada al backend de Java
 */

'use client';

import { useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation'; // Para redirigir después del registro exitoso
import {
  User,
  Mail,
  Lock,
  Eye,
  EyeOff,
  UserPlus,
  ArrowLeft,
  TrendingUp,
  ShieldCheck
} from 'lucide-react';

export default function RegisterPage() {
  const router = useRouter();
  const [showPassword, setShowPassword] = useState(false);
  
  // Estados para capturar los datos del nuevo usuario
  const [nombre, setNombre] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    console.log('Intentando registrar usuario en Apache Tomcat...', { nombre, email, password });

    try {
      // 1. Empaquetamos los datos en formato URLSearchParams para request.getParameter()
      const params = new URLSearchParams();
      params.append('nombre', nombre);
      params.append('correo', email);
      params.append('contrasena', password);

      // 2. Enviamos la petición POST a tu nuevo servlet de registro
      const response = await fetch('http://localhost:8080/FinanziApp/registro', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: params.toString()
      });

      if (response.ok) {
        const data = await response.json();
        
        if (data.estado) {
          alert(data.mensaje || '¡Cuenta creada con éxito!');
          // Redirigimos automáticamente al usuario a la pantalla de login
          router.push('/login');
        } else {
          alert(data.mensaje || 'No se pudo crear la cuenta.');
        }
      } else {
        console.error('Error en el servidor al registrar:', response.status);
        alert('Hubo un problema en el servidor al intentar registrar el usuario.');
      }

    } catch (error) {
      console.error('Error de red:', error);
      alert('No se pudo establecer conexión con el backend de Java. Verifica si Tomcat está corriendo.');
    }
  };

  return (
    <div className="min-h-screen w-full grid lg:grid-cols-2 bg-background">

      {/* ---------------- PANEL IZQUIERDO: DEGRADADO PREMIUM (Solo Desktop) ---------------- */}
      <div className="hidden lg:flex flex-col justify-between p-12 relative overflow-hidden bg-gradient-to-br from-emerald-950 via-emerald-900 to-background border-r">
        <div className="absolute top-0 left-0 w-96 h-96 bg-primary/10 rounded-full blur-3xl -translate-x-1/2 -translate-y-1/2" />
        <div className="absolute bottom-0 right-0 w-96 h-96 bg-emerald-500/5 rounded-full blur-3xl translate-x-1/3 translate-y-1/3" />

        {/* Logo de FinanziApp */}
        <div className="flex items-center gap-2 relative z-10">
          <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-primary text-primary-foreground">
            <TrendingUp className="h-5 w-5" />
          </div>
          <span className="text-xl font-bold text-white tracking-tight">
            Finanzi<span className="text-primary">App</span>
          </span>
        </div>

        {/* Mensaje motivacional de registro */}
        <div className="max-w-md my-auto relative z-10 space-y-4">
          <h1 className="text-4xl font-extrabold tracking-tight text-white leading-tight">
            Comienza tu viaje <br />financiero hoy
          </h1>
          <p className="text-emerald-100/70 text-sm leading-relaxed">
            Únete a FinanziApp y obtén herramientas avanzadas para organizar tus presupuestos, trazar metas inteligentes y construir un futuro económico sólido.
          </p>
        </div>

        <div className="text-xs text-emerald-100/40 relative z-10 flex items-center gap-1.5">
          <ShieldCheck className="h-4 w-4 text-primary/60" />
          Tus credenciales se procesan de forma privada y segura.
        </div>
      </div>

      {/* ---------------- PANEL DERECHO: FORMULARIO DE REGISTRO ---------------- */}
      <div className="flex flex-col justify-center items-center px-6 py-12 lg:p-16 h-full relative">
        
        {/* Enlace flotante superior para regresar al login de manera sutil */}
        <div className="absolute top-8 right-8 hidden sm:block">
          <Link 
            href="/login" 
            className="text-xs font-medium text-muted-foreground hover:text-foreground flex items-center gap-1 transition-colors"
          >
            <ArrowLeft className="h-3.5 w-3.5" /> Volver al inicio de sesión
          </Link>
        </div>

        <div className="w-full max-w-[400px] space-y-6">
          
          {/* Cabecera */}
          <div className="space-y-2 text-center lg:text-left">
            <h2 className="text-2xl font-bold tracking-tight text-foreground">
              Crear una cuenta
            </h2>
            <p className="text-sm text-muted-foreground">
              Ingresa tus datos a continuación para registrarte en la plataforma.
            </p>
          </div>

          {/* Formulario */}
          <form onSubmit={handleSubmit} className="space-y-4">

            {/* Input Nombre */}
            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-foreground tracking-wide">
                Nombre completo
              </label>
              <div className="relative">
                <User className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                <input
                  type="text"
                  required
                  placeholder="Tu nombre y apellido"
                  value={nombre}
                  onChange={(e) => setNombre(e.target.value)}
                  className="w-full pl-10 pr-4 py-2.5 bg-background border rounded-lg text-sm placeholder:text-muted-foreground/60 focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all"
                />
              </div>
            </div>

            {/* Input Correo */}
            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-foreground tracking-wide">
                Correo electrónico
              </label>
              <div className="relative">
                <Mail className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                <input
                  type="email"
                  required
                  placeholder="nombre@ejemplo.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="w-full pl-10 pr-4 py-2.5 bg-background border rounded-lg text-sm placeholder:text-muted-foreground/60 focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all"
                />
              </div>
            </div>

            {/* Input Contraseña */}
            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-foreground tracking-wide">
                Contraseña
              </label>
              <div className="relative">
                <Lock className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                <input
                  type={showPassword ? 'text' : 'password'}
                  required
                  placeholder="Crea una contraseña segura"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="w-full pl-10 pr-10 py-2.5 bg-background border rounded-lg text-sm placeholder:text-muted-foreground/60 focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all"
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground transition-colors"
                >
                  {showPassword ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
                </button>
              </div>
            </div>

            {/* Botón enviar */}
            <button
              type="submit"
              className="w-full mt-2 flex items-center justify-center gap-2 rounded-lg bg-primary px-4 py-2.5 text-sm font-semibold text-primary-foreground shadow-sm hover:bg-primary/90 transition-colors"
            >
              <UserPlus className="h-4 w-4" />
              Registrar cuenta
            </button>
          </form>

          {/* Enlace inferior para móvil o pantallas pequeñas */}
          <div className="text-center pt-2">
            <p className="text-xs text-muted-foreground">
              ¿Ya tienes una cuenta?{' '}
              <Link href="/login" className="font-semibold text-primary hover:underline">
                Inicia sesión aquí
              </Link>
            </p>
          </div>

        </div>
      </div>

    </div>
  );
}