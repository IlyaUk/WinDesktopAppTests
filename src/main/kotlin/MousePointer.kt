package ru.ifmo.javajna

import com.sun.jna.platform.win32.BaseTSD.ULONG_PTR
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef.DWORD
import com.sun.jna.platform.win32.WinDef.LONG
import com.sun.jna.platform.win32.WinUser.INPUT
import java.io.IOException

/**
 * Mouse related methods and values.
 */
object MousePointer {
    const val MOUSEEVENTF_MOVE = 1
    const val MOUSEEVENTF_LEFTDOWN = 2
    const val MOUSEEVENTF_LEFTUP = 4
    const val MOUSEEVENTF_RIGHTDOWN = 8
    const val MOUSEEVENTF_RIGHTUP = 16
    const val MOUSEEVENTF_MIDDLEDOWN = 32
    const val MOUSEEVENTF_MIDDLEUP = 64
    const val MOUSEEVENTF_WHEEL = 2048

    /**
     * Moves the mouse relative to it's current position.
     *
     * @param x
     * Horizontal movement
     * @param y
     * Vertical movement
     */
    fun mouseMove(x: Int, y: Int) {
        mouseAction(x, y, MOUSEEVENTF_MOVE)
    }

    /**
     * Sends a left-click input at the given x,y coordinates. If -1 is given for
     * each of the inputs it will send the input to the current position of the
     * mouse.
     *
     * @param x
     * @param y
     */
    private fun mouseLeftClick(x: Int, y: Int) {
        mouseAction(x, y, MOUSEEVENTF_LEFTDOWN)
        mouseAction(x, y, MOUSEEVENTF_LEFTUP)
    }

    /**
     * Sends a right-click input at the given x,y coordinates. If -1 is given for
     * each of the inputs it will send the input to the current position of the
     * mouse.
     *
     * @param x
     * @param y
     */
    private fun mouseRightClick(x: Int, y: Int) {
        mouseAction(x, y, MOUSEEVENTF_RIGHTDOWN)
        mouseAction(x, y, MOUSEEVENTF_RIGHTUP)
    }

    /**
     * Sends a middle-click input at the given x,y coordinates. If -1 is given for
     * each of the inputs it will send the input to the current position of the
     * mouse.
     *
     * @param x
     * @param y
     */
    fun mouseMiddleClick(x: Int, y: Int) {
        mouseAction(x, y, MOUSEEVENTF_MIDDLEDOWN)
        mouseAction(x, y, MOUSEEVENTF_MIDDLEUP)
    }

    /**
     * Sends an action (flags) at the given x,y coordinates.
     *
     * @param x
     * @param y
     * @param flags
     */
    private fun mouseAction(x: Int, y: Int, flags: Int) {
        val input = INPUT()
        input.type = DWORD(INPUT.INPUT_MOUSE.toLong())
        input.input.setType("mi")
        if (x != -1) {
            input.input.mi.dx = LONG(x.toLong())
        }
        if (y != -1) {
            input.input.mi.dy = LONG(y.toLong())
        }
        input.input.mi.time = DWORD(0)
        input.input.mi.dwExtraInfo = ULONG_PTR(0)
        input.input.mi.dwFlags = DWORD(flags.toLong())
        User32.INSTANCE.SendInput(DWORD(1), arrayOf<INPUT>(input), input.size())
    }

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        mouseRightClick(500, 500)
    }
}