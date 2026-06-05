/**
 * @file components/layout/bottom-nav.tsx
 * @description Navegación inferior para vista mobile
 * Barra de navegación con iconos y etiquetas
 */

'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import {
  Home,
  Wallet,
  PieChart,
  Target,
  BarChart2,
  GraduationCap,
  Settings,
  Tag,
} from 'lucide-react';
import { cn } from '@/lib/utils';

/**
 * Mapeo de iconos para la navegación
 */
const iconMap: Record<string, React.ComponentType<{ className?: string }>> = {
  'home': Home,
  'wallet': Wallet,
  'pie-chart': PieChart,
  'target-2': Target,
  'bar-chart': BarChart2,
  'graduation-cap': GraduationCap,
  'settings': Settings,
  'tag': Tag,
};

/**
 * Elementos de navegación inferior
 */
const navItems = [
  { id: 'home', label: 'Home', icon: 'home', href: '/' },
  //{ id: 'cuentas', label: 'Cuentas', icon: 'wallet', href: '/cuentas' },
  { id: 'presupuestos', label: 'Presupuestos', icon: 'pie-chart', href: '/presupuestos' },
  { id: 'metas', label: 'Metas', icon: 'target-2', href: '/metas' },
  { id: 'informes', label: 'Informes', icon: 'bar-chart', href: '/informes' },
  { id: 'categorias', label: 'Categorías', icon: 'tag', href: '/categorias' },
  { id: 'ajustes', label: 'Ajustes', icon: 'settings', href: '/ajustes' },
];

/**
 * Componente BottomNav
 * Navegación inferior fija visible solo en dispositivos móviles
 */
export function BottomNav() {
  const pathname = usePathname();
  
  return (
    <nav className="fixed bottom-0 left-0 right-0 z-50 border-t bg-card lg:hidden">
      <div className="flex items-center justify-around py-2">
        {navItems.map((item) => {
          const Icon = iconMap[item.icon];
          const isActive = pathname === item.href || 
            (item.href !== '/' && pathname.startsWith(item.href));
          
          return (
            <Link
              key={item.id}
              href={item.href}
              className={cn(
                'flex flex-col items-center gap-1 px-2 py-1 text-xs transition-colors',
                isActive
                  ? 'text-primary'
                  : 'text-muted-foreground hover:text-foreground'
              )}
            >
              {Icon && <Icon className="h-5 w-5" />}
              <span className="truncate">{item.label}</span>
            </Link>
          );
        })}
      </div>
    </nav>
  );
}
