---
name: Prestige Metallic
colors:
  surface: '#faf9f7'
  surface-dim: '#dadad8'
  surface-bright: '#faf9f7'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f4f3f1'
  surface-container: '#efeeec'
  surface-container-high: '#e9e8e6'
  surface-container-highest: '#e3e2e0'
  on-surface: '#1a1c1b'
  on-surface-variant: '#4d4639'
  inverse-surface: '#2f3130'
  inverse-on-surface: '#f1f1ef'
  outline: '#7f7667'
  outline-variant: '#d1c5b4'
  surface-tint: '#775a19'
  primary: '#5d4300'
  on-primary: '#ffffff'
  primary-container: '#775a19'
  on-primary-container: '#fcd386'
  inverse-primary: '#e8c176'
  secondary: '#5e5e5f'
  on-secondary: '#ffffff'
  secondary-container: '#e1dfe0'
  on-secondary-container: '#626263'
  tertiary: '#464746'
  on-tertiary: '#ffffff'
  tertiary-container: '#5d5f5d'
  on-tertiary-container: '#d9d9d7'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffdea3'
  primary-fixed-dim: '#e8c176'
  on-primary-fixed: '#261900'
  on-primary-fixed-variant: '#5c4200'
  secondary-fixed: '#e4e2e3'
  secondary-fixed-dim: '#c7c6c7'
  on-secondary-fixed: '#1b1c1d'
  on-secondary-fixed-variant: '#464748'
  tertiary-fixed: '#e2e3e0'
  tertiary-fixed-dim: '#c6c7c5'
  on-tertiary-fixed: '#1a1c1b'
  on-tertiary-fixed-variant: '#454746'
  background: '#faf9f7'
  on-background: '#1a1c1b'
  surface-variant: '#e3e2e0'
typography:
  display-lg:
    fontFamily: Playfair Display
    fontSize: 64px
    fontWeight: '700'
    lineHeight: '1.1'
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Playfair Display
    fontSize: 40px
    fontWeight: '600'
    lineHeight: '1.2'
  headline-lg-mobile:
    fontFamily: Playfair Display
    fontSize: 32px
    fontWeight: '600'
    lineHeight: '1.2'
  headline-md:
    fontFamily: Playfair Display
    fontSize: 24px
    fontWeight: '600'
    lineHeight: '1.3'
  body-lg:
    fontFamily: Manrope
    fontSize: 18px
    fontWeight: '400'
    lineHeight: '1.6'
  body-md:
    fontFamily: Manrope
    fontSize: 16px
    fontWeight: '400'
    lineHeight: '1.6'
  label-md:
    fontFamily: Manrope
    fontSize: 14px
    fontWeight: '600'
    lineHeight: '1.2'
    letterSpacing: 0.1em
  label-sm:
    fontFamily: Manrope
    fontSize: 12px
    fontWeight: '500'
    lineHeight: '1.2'
    letterSpacing: 0.05em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  margin-mobile: 1.5rem
  margin-desktop: 5rem
  gutter: 2rem
  section-gap: 8rem
  stack-sm: 0.5rem
  stack-md: 1rem
  stack-lg: 2rem
---

## Brand & Style

This design system is defined by an atmosphere of **high-end luxury and editorial elegance**. It targets a sophisticated audience that values exclusivity, craftsmanship, and timeless beauty. 

The visual style is a fusion of **Modern Minimalism** and **Tactile Luxury**. By utilizing the metallic finishes of gold and silver against expansive whitespace (or deep monochromatic voids), the UI feels more like a luxury boutique than a digital interface. The aesthetic emphasizes quality over quantity, using generous margins and high-contrast typography to create a sense of prestige. Every interaction should feel intentional, smooth, and refined, evoking the same emotional response as handling a piece of fine jewelry or a high-fashion editorial.

## Colors

The palette is derived directly from the metallic gradients of the brand logo. 

*   **Primary (Gold):** Used for key brand moments, call-to-action backgrounds, and active states. It represents warmth and premium quality.
*   **Secondary (Silver/Metallic):** Used for subtle accents, borders, and secondary interactive elements to provide a cool, balanced contrast to the gold.
*   **Tertiary (Onyx):** A deep, near-black used for primary text and high-contrast backgrounds to ensure the metallics "pop."
*   **Neutral (Alabaster):** An off-white, warm neutral used for page backgrounds to prevent the clinical feel of pure white, enhancing the luxury feel.

Implement gradients for primary elements to simulate a "brushed gold" effect (from #D4AF37 to #C5A059).

## Typography

The typography strategy pairs a high-contrast serif for headlines with a clean, modern sans-serif for functional text.

**Playfair Display** is the voice of the brand, used for displays and headlines. Its sharp serifs and variable stroke weights mirror the elegant curves of the logo. 

**Manrope** provides a functional, balanced foundation for body text and labels. It ensures readability without distracting from the editorial impact of the headlines. 

For a truly "Diva" feel, use `label-md` with its heavy letter-spacing and uppercase styling for section headers and category tags to mimic luxury magazine layouts.

## Layout & Spacing

This design system uses a **fixed grid** approach for desktop to control line lengths and maintain an editorial composition. 

*   **Desktop:** 12-column grid with a max-width of 1440px. Gutters are generous (32px) to allow the content to "breathe." 
*   **Mobile:** 4-column fluid grid with 24px margins.
*   **Philosophy:** Vertical rhythm is intentionally loose. Use `section-gap` to separate distinct content blocks, forcing the user to focus on one story at a time. Whitespace is considered a design element as important as the imagery itself.

## Elevation & Depth

Hierarchy is established through **Tonal Layers** and **Subtle Metallic Outlines** rather than aggressive shadows.

1.  **Base Layer:** The Alabaster neutral background.
2.  **Surface Layer:** White or slightly tinted cards with 1px Silver (Secondary) borders.
3.  **Floating Elements:** Use extremely soft, ambient shadows (0px 20px 40px rgba(0,0,0,0.04)) to suggest depth for navigation bars or modals.
4.  **Glassmorphism:** Use for overlays and mobile navigation menus with a `blur(12px)` and a 10% white tint to maintain a "frosted glass" luxury feel.

## Shapes

The shape language reflects the logo's enclosure—a "squircle" or soft-rectangular form. 

Standard components use `rounded` (0.5rem) to feel approachable but structured. For high-fashion imagery and featured cards, use `rounded-xl` (1.5rem) to mimic the logo container's elegant silhouette. 

Avoid sharp 0px corners, as they appear too clinical, and avoid full pill-shapes for buttons, as they can appear too "app-like" and casual for a luxury brand.

## Components

*   **Buttons:** Primary buttons use a linear gradient (Gold) with Tertiary (Onyx) text for maximum impact. Secondary buttons should be "Ghost" style with a 1px Silver border and Gold text.
*   **Inputs:** Minimalist bottom-border only or very light 1px outlines. Focus states transition the border from Silver to Gold.
*   **Cards:** Use generous padding (2rem) and the "squircle" corner radius. Titles should always be in Playfair Display.
*   **Chips/Tags:** Small, uppercase Manrope text inside a light Silver-bordered container with no background fill.
*   **Lists:** Separated by thin 0.5px Silver dividers. Increase padding between items to 1.5rem to maintain the luxury spacing.
*   **Navigation:** A center-aligned logo in the header with spaced-out navigation links in `label-md` style.