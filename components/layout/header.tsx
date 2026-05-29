/**
 * @file components/layout/header.tsx
 * @description Componente de cabecera de la aplicación
 * Incluye el logo, notificaciones y avatar del usuario
 */

'use client';

import { Bell } from 'lucide-react';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';

/**
 * Logo de FinanziApp con icono de billete
 */
function Logo() {
  return (
    <div className="flex items-center gap-2">
      <div className="flex h-8 w-8 items-center justify-center rounded-lg bg-primary/10">
        <img
          src="/logo_finanziapp.png"
          alt="logo FinanziApp"
          className="h-8 w-8 object-contain"
        />
      </div>
      <span className="text-xl font-bold">
        <span className="text-primary">Finanzi</span>
        <span className="text-foreground">App</span>
      </span>
    </div>
  );
}

/**
 * Componente Header principal
 * Muestra el logo, botón de notificaciones con badge y menú de usuario
 */
export function Header() {
  return (
    <header className="sticky top-0 z-50 flex h-16 items-center justify-between border-b bg-card px-4 lg:px-6">
      {/* Logo - visible solo en mobile */}
      <div className="lg:hidden">
        <Logo />
      </div>
      
      {/* Título del Dashboard - visible solo en desktop */}
      <h1 className="hidden text-2xl font-bold text-foreground lg:block">Dashboard</h1>
      
      {/* Acciones del header */}
      <div className="flex items-center gap-3">
        {/* Botón de notificaciones */}
        <Button variant="ghost" size="icon" className="relative">
          <Bell className="h-5 w-5 text-muted-foreground" />
          {/* Badge de notificaciones */}
          <span className="absolute -right-0.5 -top-0.5 flex h-4 w-4 items-center justify-center rounded-full bg-accent text-[10px] font-medium text-accent-foreground">
            1
          </span>
          <span className="sr-only">Notificaciones</span>
        </Button>
        
        {/* Menú de usuario */}
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="relative h-9 w-9 rounded-full">
              <Avatar className="h-9 w-9">
                <AvatarImage src="/avatar.jpg" alt="Usuario" />
                <AvatarFallback className="bg-orange-100 text-orange-600">
                  US
                </AvatarFallback>
              </Avatar>
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end" className="w-56">
            <DropdownMenuLabel>Mi Cuenta</DropdownMenuLabel>
            <DropdownMenuSeparator />
            <DropdownMenuItem>Perfil</DropdownMenuItem>
            <DropdownMenuItem>Configuración</DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem className="text-destructive">
              Cerrar sesión
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
    </header>
  );
}

/**
 * Componente Logo exportado para uso en sidebar
 */
export { Logo };
