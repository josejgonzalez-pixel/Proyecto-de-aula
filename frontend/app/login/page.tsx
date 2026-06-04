/**
 * @file app/login/page.tsx
 * @description Pantalla de inicio de sesión inspirada en diseño premium FinTech
 */

'use client';

import { useState } from 'react';
import Link from 'next/link';
import {
  Eye,
  EyeOff,
  Mail,
  Lock,
  ArrowRight,
  Plus,
  TrendingUp,
  ArrowDownCircle,
  Target,
  GraduationCap,
  ShieldCheck
} from 'lucide-react';
import { useRouter } from 'next/navigation';

export default function LoginPage() {
  const router = useRouter();
  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    console.log('Intentando conectar con Apache Tomcat...', { email, password });

    try {
      //Parametros en el formato que resquest.getParameter() espera en el backend de Java
      const params = new URLSearchParams();
      params.append('correo', email);
      params.append('contrasena', password);

      // Apuntamos a la ruta correcta del urlPattern: /login
      const response = await fetch('http://localhost:8080/FinanziApp/api/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: params.toString(),
      });

      const data = await response.json();
      if (data.estado) {
      document.cookie = `auth_token=true; path=/; max-age=86400; SameSite=Lax`; // Guarda el token en una cookie por 1 hora

      console.log('Login exitoso, redirigiendo al dashboard...');
      router.push('/dashboard');
      }else {
        alert(data.mensaje || 'Correo o contraseña incorrectos');
      }

    } catch (error) {
      console.error('Error de red:', error);
      alert('No se pudo establecer conexión con el backend de Java.');
    }
  };

  return (
    <div className="min-h-screen w-full grid lg:grid-cols-2 bg-background">

      {/* ---------------- PANEL IZQUIERDO: DESCRIPTIVO (Solo visible en desktop) ---------------- */}
      <div className="hidden lg:flex flex-col justify-between p-12 relative overflow-hidden bg-gradient-to-br from-emerald-950 via-emerald-900 to-background border-r">
        {/* Efecto de luces de fondo */}
        <div className="absolute top-0 left-0 w-96 h-96 bg-primary/10 rounded-full blur-3xl -translate-x-1/2 -translate-y-1/2" />
        <div className="absolute bottom-0 right-0 w-96 h-96 bg-emerald-500/5 rounded-full blur-3xl translate-x-1/3 translate-y-1/3" />

        {/* Encabezado con Logo simplificado */}
        <div className="flex items-center gap-2 relative z-10">
          <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-primary text-primary-foreground">
            <TrendingUp className="h-5 w-5" />
          </div>
          <span className="text-xl font-bold text-white tracking-tight">
            Finanzi<span className="text-primary">App</span>
          </span>
        </div>

        {/* Contenido Central: Mensaje y Tarjetas flotantes sugeridas */}
        <div className="max-w-md my-auto relative z-10 space-y-8">
          <div className="space-y-3">
            <h1 className="text-4xl font-extrabold tracking-tight text-white leading-tight">
              Toma el control <br />de tus finanzas
            </h1>
            <p className="text-emerald-100/70 text-sm leading-relaxed">
              Administra tus ingresos, controla tus egresos, alcanza tus metas de ahorro y aprende cada día con nuestra plataforma inteligente.
            </p>
          </div>

          {/* Grid de características simulando botones flotantes minimalistas */}
          <div className="grid grid-cols-2 gap-4">
            <div className="flex items-center gap-3 bg-white/[0.04] backdrop-blur-md border border-white/10 rounded-xl p-3.5 transition-all hover:bg-white/[0.07]">
              <div className="p-2 rounded-lg bg-emerald-500/20 text-emerald-400">
                <TrendingUp className="h-4 w-4" />
              </div>
              <span className="text-xs font-semibold text-white">Ingresos</span>
            </div>
            <div className="flex items-center gap-3 bg-white/[0.04] backdrop-blur-md border border-white/10 rounded-xl p-3.5 transition-all hover:bg-white/[0.07]">
              <div className="p-2 rounded-lg bg-red-500/20 text-red-400">
                <ArrowDownCircle className="h-4 w-4" />
              </div>
              <span className="text-xs font-semibold text-white">Egresos</span>
            </div>
            <div className="flex items-center gap-3 bg-white/[0.04] backdrop-blur-md border border-white/10 rounded-xl p-3.5 transition-all hover:bg-white/[0.07]">
              <div className="p-2 rounded-lg bg-amber-500/20 text-amber-400">
                <Target className="h-4 w-4" />
              </div>
              <span className="text-xs font-semibold text-white">Metas</span>
            </div>
            <div className="flex items-center gap-3 bg-white/[0.04] backdrop-blur-md border border-white/10 rounded-xl p-3.5 transition-all hover:bg-white/[0.07]">
              <div className="p-2 rounded-lg bg-blue-500/20 text-blue-400">
                <GraduationCap className="h-4 w-4" />
              </div>
              <span className="text-xs font-semibold text-white">Aprender</span>
            </div>
          </div>
        </div>

        {/* Footer del panel izquierdo */}
        <div className="text-xs text-emerald-100/40 relative z-10 flex items-center gap-1.5">
          <ShieldCheck className="h-4 w-4 text-primary/60" />
          Tu información está protegida con cifrado avanzado.
        </div>
      </div>

      {/* ---------------- PANEL DERECHO: FORMULARIO DE LOGIN ---------------- */}
      <div className="flex flex-col justify-center items-center px-6 py-12 lg:p-16 h-full relative">

        {/* Contenedor centralizado del formulario */}
        <div className="w-full max-w-[400px] space-y-6">

          {/* Cabecera del formulario */}
          <div className="space-y-2 text-center lg:text-left">
            <h2 className="text-2xl font-bold tracking-tight text-foreground">
              ¡Bienvenido de nuevo!
            </h2>
            <p className="text-sm text-muted-foreground">
              Inicia sesión para continuar gestionando tus finanzas.
            </p>
          </div>

          {/* Formulario Nativo */}
          <form onSubmit={handleLogin} className="space-y-4">

            {/* Input Correo electrónico */}
            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-foreground tracking-wide">
                Correo electrónico
              </label>
              <div className="relative">
                <Mail className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                <input
                  type="email"
                  required
                  placeholder="Ingresa tu correo electrónico"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="w-full pl-10 pr-4 py-2.5 bg-background border rounded-lg text-sm placeholder:text-muted-foreground/60 focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all"
                />
              </div>
            </div>

            {/* Input Contraseña */}
            <div className="space-y-1.5">
              <div className="flex items-center justify-between">
                <label className="text-xs font-semibold text-foreground tracking-wide">
                  Contraseña
                </label>
                <Link
                  href="/forgot-password"
                  className="text-xs font-medium text-primary hover:underline transition-all"
                >
                  ¿Olvidaste tu contraseña?
                </Link>
              </div>
              <div className="relative">
                <Lock className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                <input
                  type={showPassword ? 'text' : 'password'}
                  required
                  placeholder="Ingresa tu contraseña"
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

            {/* Botón Principal: Iniciar Sesión */}
            <button
              type="submit"
              className="w-full mt-2 flex items-center justify-center gap-2 rounded-lg bg-primary px-4 py-2.5 text-sm font-semibold text-primary-foreground shadow-sm hover:bg-primary/90 transition-colors"
            >
              <ArrowRight className="h-4 w-4" />
              Iniciar sesión
            </button>

            {/* Botón Secundario: Crear Cuenta */}
            <Link
              href="/register"
              className="w-full flex items-center justify-center gap-2 rounded-lg border bg-card px-4 py-2.5 text-sm font-semibold text-foreground shadow-sm hover:bg-muted transition-colors"
            >
              <Plus className="h-4 w-4" />
              Crear cuenta
            </Link>
          </form>

        </div>
      </div>

    </div>
  );
}