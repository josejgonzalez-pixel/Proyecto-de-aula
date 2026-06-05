/**
 * @file components/layout/sidebar.tsx
 * @description Sidebar de navegación para vista desktop
 */

'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import {
  LayoutDashboard,
  Wallet,
  PieChart,
  Target,
  BarChart2,
  Settings,
  Tag,
} from 'lucide-react';
import { cn } from '../../lib/utils';
import { Logo } from './header';

/**
 * Elementos de navegación del sidebar
 * He unificado la estructura para usar los componentes de íconos directamente
 */
const sidebarItems = [
  { id: 'dashboard', label: 'Dashboard', icon: LayoutDashboard, href: '/' },
  //{ id: 'cuentas', label: 'Cuentas', icon: Wallet, href: '/cuentas' },
  { id: 'presupuestos', label: 'Presupuestos', icon: PieChart, href: '/presupuestos' },
  { id: 'metas', label: 'Metas', icon: Target, href: '/metas' },
  { id: 'informes', label: 'Informes', icon: BarChart2, href: '/informes' },
  { id: 'categorias', label: 'Categorías', icon: Tag, href: '/categorias' },
];

export function Sidebar() {
  const pathname = usePathname();
  
  return (
    <aside className="hidden lg:flex lg:w-64 lg:flex-col lg:border-r lg:bg-card h-screen">

      {/* Logo */}
      <div className="flex h-16 items-center border-b px-6 shrink-0">
        <Logo />
      </div>
      
      <div className="grid grid-rows-[1fr_auto] flex-1 min-h-0">

        {/* Navegación Principal */}
        <nav className="space-y-1 p-4 overflow-y-auto">
          {sidebarItems.map((item) => {
            const Icon = item.icon; // Usamos el componente directamente
            const isActive = pathname === item.href;
            
            return (
              <Link
                key={item.id}
                href={item.href}
                className={cn(
                  'flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium transition-colors',
                  isActive
                    ? 'bg-primary/10 text-primary'
                    : 'text-muted-foreground hover:bg-muted hover:text-foreground'
                )}
              >
                <Icon className="h-5 w-5" />
                {item.label}
              </Link>
            );
          })}
        </nav>
        
        {/* Configuración al final */}
        <div className="border-t p-4 bg-card shrink-0">
          <Link
            href="/configuracion"
            className={cn(
              'flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium transition-colors',
              pathname === '/configuracion'
                ? 'bg-primary/10 text-primary'
                : 'text-muted-foreground hover:bg-muted hover:text-foreground'
            )}
          >
            <Settings className="h-5 w-5" />
            Configuración
          </Link>
        </div>

      </div>
    </aside>
  );
}