<script>
	import { Button, Input, Label } from 'flowbite-svelte';
</script>

<div class="w-full max-w-md rounded-xl border border-[#e3e2df] bg-white p-8 shadow-none">
	<h2 class="mb-8 text-center text-2xl font-semibold tracking-tight text-[#22223b]">
		Sign up for an account
	</h2>
	<form class="space-y-6">
		<div>
			<Label for="email" class="mb-1 block text-[15px] font-medium text-[#444]">Email</Label>
			<Input
				id="email"
				type="email"
				placeholder="you@example.com"
				required
				class="w-full rounded-md border border-[#e3e2df] bg-[#fafaf9] px-3 py-2 text-[15px] shadow-none focus:border-[#a3a3a3] focus:ring-0"
			/>
		</div>
		<div>
			<Label for="password" class="mb-1 block text-[15px] font-medium text-[#444]">Password</Label>
			<Input
				id="password"
				type="password"
				placeholder="Password"
				required
				class="w-full rounded-md border border-[#e3e2df] bg-[#fafaf9] px-3 py-2 text-[15px] shadow-none focus:border-[#a3a3a3] focus:ring-0"
			/>
		</div>
		<div>
			<Label for="confirmPassword" class="mb-1 block text-[15px] font-medium text-[#444]"
				>Password</Label
			>
			<Input
				id="password"
				type="password"
				placeholder="Password"
				required
				class="w-full rounded-md border border-[#e3e2df] bg-[#fafaf9] px-3 py-2 text-[15px] shadow-none focus:border-[#a3a3a3] focus:ring-0"
			/>
		</div>
		<div class="flex justify-end">
			<Button type="submit">Sign Up</Button>
		</div>
	</form>
</div>
